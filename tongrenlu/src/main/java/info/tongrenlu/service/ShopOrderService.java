package info.tongrenlu.service;

import info.tongrenlu.domain.OrderBean;
import info.tongrenlu.domain.OrderItemBean;
import info.tongrenlu.domain.ShopBean;
import info.tongrenlu.domain.UserBean;
import info.tongrenlu.http.HttpWraper;
import info.tongrenlu.manager.OrderManager;
import info.tongrenlu.manager.ShopManager;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ShopOrderService implements InitializingBean {

    public static final Pattern PATTERN_TORANOANA = Pattern.compile("http://www.toranoana.jp/mailorder/.*");
    public static final Pattern PATTERN_MELONBOOKS = Pattern.compile("https://www.melonbooks.co.jp/detail/.*");

    public static final String TORANOANA = "toranoana";
    public static final String MELONBOOKS = "melonbooks";

    private final Log log = LogFactory.getLog(this.getClass());

    @Autowired
    private OrderManager orderManager = null;
    @Autowired
    private ShopManager shopManager = null;
    @Autowired
    private MessageSource messageSource = null;
    @Autowired
    private HttpWraper toranoanaClient = null;
    @Autowired
    private HttpWraper melonbooksClient = null;

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    public OrderItemBean initWithUrl(final String nameOrUrl) {
        final ShopBean shopBean = this.shopManager.getDefaultShop();

        final OrderItemBean item = new OrderItemBean();
        item.setExchangeRate(shopBean.getExchangeRate());
        item.setFee(shopBean.getFee());
        item.setRemovable(true);

        if (PATTERN_TORANOANA.matcher(nameOrUrl).find()) {
            this.initWithToranoana(item, nameOrUrl);
            final BigDecimal price = item.getPrice();
            final BigDecimal tax = price.multiply(shopBean.getTaxRate());
            item.setPrice(price.add(tax));
        } else if (PATTERN_MELONBOOKS.matcher(nameOrUrl).find()) {
            this.initWithMelonbooks(item, nameOrUrl);
        } else {
            item.setTitle(nameOrUrl);
            item.setPrice(BigDecimal.ZERO);
        }
        item.setQuantity(BigDecimal.ONE);
        return item;
    }

    private void initWithMelonbooks(final OrderItemBean item, final String url) {
        final String html = this.melonbooksClient.getForHtml(url);
        final Document doc = Jsoup.parse(html);

        final String title = doc.select("#title strong.str").get(0).text();
        final String circleName = doc.select("#title .circle").text();
        BigDecimal price = BigDecimal.ZERO;

        // parse the string
        try {
            final Element priceElement = doc.select("#form1 .price").first();
            priceElement.children().remove();
            final String priceText = priceElement.text();
            final DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            symbols.setGroupingSeparator(',');
            symbols.setDecimalSeparator('.');
            final String pattern = "¥#,##0.0#";
            final DecimalFormat decimalFormat = new DecimalFormat(pattern,
                                                                  symbols);
            decimalFormat.setParseBigDecimal(true);
            price = (BigDecimal) decimalFormat.parse(priceText);
        } catch (final ParseException e) {
            this.log.error(e.getMessage(), e);
        }

        item.setTitle(String.format("[%s] %s", circleName, title));
        item.setShop(MELONBOOKS);
        item.setUrl(url);
        item.setPrice(price);
    }

    private void initWithToranoana(final OrderItemBean item, final String url) {
        final String html = this.toranoanaClient.getForHtml(url);
        final Document doc = Jsoup.parse(html);

        final String title = doc.select(".table_title_outerflame .td_title_bar_r1c2").first().text();
        final String circleName = doc.select(".CircleName a").text();
        BigDecimal price = BigDecimal.ZERO;

        // parse the string
        try {
            final String priceText = doc.select(".DetailData_SubmitArea span.bold").text();
            final DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            symbols.setGroupingSeparator(',');
            symbols.setDecimalSeparator('.');
            final String pattern = "#,##0.0#";
            final DecimalFormat decimalFormat = new DecimalFormat(pattern,
                                                                  symbols);
            decimalFormat.setParseBigDecimal(true);
            price = (BigDecimal) decimalFormat.parse(priceText);
        } catch (final ParseException e) {
            this.log.error(e.getMessage(), e);
        }

        item.setTitle(String.format("[%s] %s", circleName, title));
        item.setShop(TORANOANA);
        item.setUrl(url);
        item.setPrice(price);
    }

    public boolean newOrder(final OrderBean orderBean,
                            final Collection<OrderItemBean> itemList,
                            final Locale locale) {
        final OrderItemBean firstItem = (OrderItemBean) CollectionUtils.get(itemList,
                                                                            0);
        String title = firstItem.getTitle();

        if (CollectionUtils.size(itemList) > 1) {
            title = this.messageSource.getMessage("order.title.etc",
                                                  new String[] { title },
                                                  locale);
        }

        orderBean.setTitle(title);

        this.orderManager.insertOrder(orderBean);
        this.orderManager.insertOrderItems(itemList);
        return true;
    }

    public OrderBean makeOrderBean(final UserBean userBean) {
        final OrderBean orderBean = new OrderBean();
        orderBean.setUserBean(userBean);
        return orderBean;
    }

    public List<OrderItemBean> makeItemList(final Map<String, OrderItemBean> shoppingCart,
                                            final OrderBean orderBean,
                                            final Locale locale) {
        final ShopBean shopBean = this.shopManager.getDefaultShop();

        final List<OrderItemBean> itemList = new ArrayList<>();
        if (!CollectionUtils.sizeIsEmpty(shoppingCart)) {
            BigDecimal amountJp = BigDecimal.ZERO;
            BigDecimal amountCn = BigDecimal.ZERO;
            BigDecimal fee = BigDecimal.ZERO;
            BigDecimal total = BigDecimal.ZERO;

            final Map<String, BigDecimal> shippingMap = new HashMap<>();

            for (final OrderItemBean item : shoppingCart.values()) {
                item.setOrderBean(orderBean);
                item.setUserBean(orderBean.getUserBean());

                amountJp = amountJp.add(item.getAmountJp());
                amountCn = amountCn.add(item.getAmountCn());
                fee = fee.add(item.getFee());
                total = total.add(item.getTotal());
                itemList.add(item);

                if (StringUtils.isNotBlank(item.getShop())) {
                    final String shop = item.getShop();
                    BigDecimal shopTotal = shippingMap.get(shop);
                    if (shopTotal == null) {
                        shopTotal = BigDecimal.ZERO;
                    }
                    shopTotal = shopTotal.add(item.getAmountJp());
                    shippingMap.put(shop, shopTotal);
                }
            }

            for (final Entry<String, BigDecimal> entry : shippingMap.entrySet()) {
                final String shop = entry.getKey();
                final BigDecimal shopTotal = entry.getValue();

                if (shop.equals(TORANOANA) && shopTotal.compareTo(BigDecimal.valueOf(15000)) < 0) {
                    final OrderItemBean item = new OrderItemBean();
                    item.setOrderBean(orderBean);
                    item.setUserBean(orderBean.getUserBean());
                    item.setTitle(this.messageSource.getMessage("order.shipping",
                                                                new String[] { TORANOANA },
                                                                null));
                    item.setExchangeRate(shopBean.getExchangeRate());
                    item.setFee(BigDecimal.ZERO);
                    item.setPrice(BigDecimal.valueOf(500));
                    item.setQuantity(BigDecimal.ONE);
                    item.setRemovable(false);

                    amountJp = amountJp.add(item.getAmountJp());
                    amountCn = amountCn.add(item.getAmountCn());
                    fee = fee.add(item.getFee());
                    total = total.add(item.getTotal());

                    itemList.add(item);
                } else if (shop.equals(MELONBOOKS) && shopTotal.compareTo(BigDecimal.valueOf(18000)) < 0) {
                    final OrderItemBean item = new OrderItemBean();
                    item.setOrderBean(orderBean);
                    item.setUserBean(orderBean.getUserBean());
                    item.setTitle(this.messageSource.getMessage("order.shipping",
                                                                new String[] { MELONBOOKS },
                                                                null));
                    item.setExchangeRate(shopBean.getExchangeRate());
                    item.setFee(BigDecimal.ZERO);
                    item.setPrice(BigDecimal.valueOf(500));
                    item.setQuantity(BigDecimal.ONE);
                    item.setRemovable(false);

                    amountJp = amountJp.add(item.getAmountJp());
                    amountCn = amountCn.add(item.getAmountCn());
                    fee = fee.add(item.getFee());
                    total = total.add(item.getTotal());

                    itemList.add(item);
                }
            }

            orderBean.setAmountJp(amountJp);
            orderBean.setAmountCn(amountCn);
            orderBean.setFee(fee);
            orderBean.setTotal(total);
        }
        return itemList;
    }

}
