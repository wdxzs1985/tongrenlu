package info.tongrenlu.service;

import info.tongrenlu.domain.OrderBean;
import info.tongrenlu.domain.OrderItemBean;
import info.tongrenlu.domain.ShopBean;
import info.tongrenlu.domain.UserBean;
import info.tongrenlu.http.HttpWraper;
import info.tongrenlu.mail.MailModel;
import info.tongrenlu.mail.MailResolvor;
import info.tongrenlu.manager.OrderItemManager;
import info.tongrenlu.manager.OrderManager;
import info.tongrenlu.manager.ShopManager;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ShopOrderService {

    public static final Pattern PATTERN_TORANOANA = Pattern.compile("http://www.toranoana.jp/mailorder/.*");
    public static final Pattern PATTERN_MELONBOOKS = Pattern.compile("https://www.melonbooks.co.jp/detail/.*");

    private final Log log = LogFactory.getLog(this.getClass());

    @Autowired
    private OrderManager orderManager = null;
    @Autowired
    private OrderItemManager orderItemManager = null;
    @Autowired
    private ShopManager shopManager = null;
    @Autowired
    private MessageSource messageSource = null;
    @Autowired
    private HttpWraper toranoanaClient = null;
    @Autowired
    private HttpWraper melonbooksClient = null;
    @Autowired
    private MailResolvor mailResolvor = null;

    public OrderItemBean initWithUrl(final String url, final Map<String, Object> model, final Locale locale) {
        final String fieldName = this.messageSource.getMessage("OrderItemBean.url", null, locale);
        if (StringUtils.isBlank(url)) {
            model.put("error", this.messageSource.getMessage("validate.empty", new Object[] { fieldName }, locale));
            return null;
        }
        final ShopBean shopBean = this.shopManager.getDefaultShop();
        final OrderItemBean item = this.initItem(shopBean);
        item.setFee(shopBean.getFeeMailorder());
        item.setTotalFee(shopBean.getFeeMailorder());

        if (PATTERN_TORANOANA.matcher(url).find()) {
            this.initWithToranoana(item, url);
            final BigDecimal price = item.getPrice();
            final BigDecimal tax = price.multiply(shopBean.getTaxRate());
            item.setPrice(price.add(tax));
        } else if (PATTERN_MELONBOOKS.matcher(url).find()) {
            this.initWithMelonbooks(item, url);
        } else {
            model.put("error", this.messageSource.getMessage("validate.bad", new Object[] { fieldName }, locale));
            return null;
        }
        return item;
    }

    public OrderItemBean initEventItem(final String title,
                                       final BigDecimal price,
                                       final String url,
                                       final Map<String, Object> model,
                                       final Locale locale) {
        if (StringUtils.isBlank(title)) {
            final String fieldName = this.messageSource.getMessage("OrderItemBean.title", null, locale);
            model.put("error", this.messageSource.getMessage("validate.empty", new Object[] { fieldName }, locale));
            return null;
        }
        final ShopBean shopBean = this.shopManager.getDefaultShop();
        final OrderItemBean item = this.initItem(shopBean);
        item.setTitle(title);
        item.setPrice(price);
        item.setUrl(url);
        item.setFee(shopBean.getFeeEvent());
        item.setTotalFee(shopBean.getFeeEvent());
        item.setShop(this.messageSource.getMessage("shop.event", null, locale));
        return item;
    }

    private OrderItemBean initItem(final ShopBean shopBean) {
        final OrderItemBean item = new OrderItemBean();
        item.setExchangeRate(shopBean.getExchangeRate());
        item.setQuantity(BigDecimal.ONE);
        item.setPrice(BigDecimal.ZERO);
        return item;
    }

    private void initWithMelonbooks(final OrderItemBean item, final String url) {
        final String html = this.melonbooksClient.getForHtml(url);
        final Document doc = Jsoup.parse(html);

        final String title = doc.select("#title strong.str").get(0).text();
        final String circleName = StringUtils.trim(doc.select("#title .circle").text());
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
            final DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
            decimalFormat.setParseBigDecimal(true);
            price = (BigDecimal) decimalFormat.parse(priceText);
        } catch (final ParseException e) {
            this.log.error(e.getMessage(), e);
        }

        item.setTitle(String.format("[%s] %s", circleName, title));
        item.setShop(this.messageSource.getMessage("shop.mailorder.melonbooks", null, null));
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
            final DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
            decimalFormat.setParseBigDecimal(true);
            price = (BigDecimal) decimalFormat.parse(priceText);
        } catch (final ParseException e) {
            this.log.error(e.getMessage(), e);
        }

        item.setTitle(String.format("[%s] %s", circleName, title));
        item.setShop(this.messageSource.getMessage("shop.mailorder.toranoana", null, null));
        item.setUrl(url);
        item.setPrice(price);
    }

    public boolean newOrder(final OrderBean orderBean, final Collection<OrderItemBean> itemList, final Locale locale) {
        final OrderItemBean firstItem = (OrderItemBean) CollectionUtils.get(itemList, 0);
        String title = firstItem.getTitle();

        if (CollectionUtils.size(itemList) > 1) {
            title = this.messageSource.getMessage("order.title.etc", new Object[] { title }, locale);
        }

        orderBean.setTitle(title);

        this.orderManager.insertOrder(orderBean);
        // this.orderManager.insertOrderItems(itemList);
        for (final OrderItemBean orderItemBean : itemList) {
            orderItemBean.setOrderBean(orderBean);
            this.orderItemManager.insert(orderItemBean);
        }

        final UserBean userBean = orderBean.getUserBean();

        final MailModel mailModel = this.mailResolvor.createMailModel(locale);
        mailModel.setSubject(this.messageSource.getMessage("mail.order.subject",
                                                           new Object[] { userBean.getNickname() },
                                                           locale));
        mailModel.setTo(this.mailResolvor.createAddress(userBean.getEmail(), userBean.getNickname()));
        mailModel.setTemplate("order_create");

        mailModel.addAttribute("userBean", userBean);
        mailModel.addAttribute("orderBean", orderBean);
        mailModel.addAttribute("itemList", itemList);

        this.mailResolvor.send(mailModel);

        return true;
    }

    public OrderBean makeOrderBean(final UserBean userBean) {
        final OrderBean orderBean = new OrderBean();
        orderBean.setUserBean(userBean);
        return orderBean;
    }

    public List<OrderItemBean> makeItemList(final Map<String, OrderItemBean> shoppingCart, final OrderBean orderBean) {

        final List<OrderItemBean> itemList = new ArrayList<>();
        if (!CollectionUtils.sizeIsEmpty(shoppingCart)) {
            BigDecimal amountJp = BigDecimal.ZERO;
            BigDecimal amountCn = BigDecimal.ZERO;
            BigDecimal fee = BigDecimal.ZERO;
            BigDecimal total = BigDecimal.ZERO;
            BigDecimal quantity = BigDecimal.ZERO;
            BigDecimal shippingFee = BigDecimal.ZERO;

            for (final OrderItemBean item : shoppingCart.values()) {
                item.setOrderBean(orderBean);
                item.setUserBean(orderBean.getUserBean());

                amountJp = amountJp.add(item.getAmountJp());
                amountCn = amountCn.add(item.getAmountCn());

                final BigDecimal totalFee = item.getFee().multiply(item.getQuantity());
                item.setTotalFee(totalFee);

                fee = fee.add(item.getTotalFee());
                total = total.add(item.getTotal());
                itemList.add(item);

                quantity = quantity.add(item.getQuantity());
            }

            switch (orderBean.getShippingMethod()) {
            case OrderBean.SHIPPING_EMS:
                shippingFee = (this.getEmsPrice(quantity));
                break;
            case OrderBean.SHIPPING_SAL:
                shippingFee = (this.getSalPrice(quantity));
                break;
            case OrderBean.SHIPPING_GROUP:
                shippingFee = (this.getGroupPrice(quantity));
                break;
            }
            total = total.add(shippingFee);

            orderBean.setQuantity(quantity);
            orderBean.setAmountJp(amountJp);
            orderBean.setAmountCn(amountCn);
            orderBean.setFee(fee);
            orderBean.setShippingFee(shippingFee);
            orderBean.setTotal(total);

        }
        return itemList;
    }

    public BigDecimal getEmsPrice(final BigDecimal quantity) {
        final Integer price = this.orderManager.getEmsPrice(quantity);
        final BigDecimal exchageRate = this.shopManager.getDefaultShop().getExchangeRate();
        return exchageRate.multiply(BigDecimal.valueOf(price));
    }

    public BigDecimal getSalPrice(final BigDecimal quantity) {
        final Integer price = this.orderManager.getSalPrice(quantity);
        final BigDecimal exchageRate = this.shopManager.getDefaultShop().getExchangeRate();
        return exchageRate.multiply(BigDecimal.valueOf(price));
    }

    public BigDecimal getGroupPrice(final BigDecimal quantity) {
        return BigDecimal.valueOf(5).multiply(quantity).add(BigDecimal.TEN);
    }
}
