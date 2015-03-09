package info.tongrenlu.service;

import info.tongrenlu.domain.OrderBean;
import info.tongrenlu.domain.OrderItemBean;
import info.tongrenlu.domain.ShopBean;
import info.tongrenlu.domain.UserBean;
import info.tongrenlu.manager.OrderManager;
import info.tongrenlu.manager.ShopManager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ShopOrderService {

    public static final Pattern PATTERN_TORANOANA = Pattern.compile("http://www.toranoana.jp/mailorder/.*");

    public static final String TORANOANA = "toranoana";

    @Autowired
    private OrderManager orderManager = null;
    @Autowired
    private ShopManager shopManager = null;

    public OrderItemBean initWithUrl(final String nameOrUrl) {
        final ShopBean shopBean = this.shopManager.getDefaultShop();

        final OrderItemBean item = new OrderItemBean();
        item.setExchangeRate(shopBean.getExchangeRate());
        item.setFee(shopBean.getFee());

        if (PATTERN_TORANOANA.matcher(nameOrUrl).find()) {
            this.initWithToranoana(item, nameOrUrl);
        } else {
            item.setTitle(nameOrUrl);
            item.setPrice(BigDecimal.ZERO);
        }
        item.setQuantity(BigDecimal.ONE);
        return item;
    }

    private void initWithToranoana(final OrderItemBean item, final String nameOrUrl) {
        item.setTitle("[Sound CYCLONE] Sparkle!");
        item.setShop(TORANOANA);
        item.setUrl(nameOrUrl);
        item.setPrice(BigDecimal.valueOf(1500));
    }

    public boolean newOrder(final OrderBean orderBean, final Collection<OrderItemBean> itemList) {
        final OrderItemBean firstItem = (OrderItemBean) CollectionUtils.get(itemList, 0);
        String title = firstItem.getTitle();

        if (CollectionUtils.size(itemList) > 1) {
            title += " etc.";
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

    public List<OrderItemBean> makeItemList(final Map<String, OrderItemBean> shoppingCart, final OrderBean orderBean) {
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

                if (shop.equals(TORANOANA) && shopTotal.compareTo(BigDecimal.valueOf(10000)) < 0) {
                    final OrderItemBean item = new OrderItemBean();
                    item.setOrderBean(orderBean);
                    item.setUserBean(orderBean.getUserBean());
                    item.setTitle("shipping for " + TORANOANA);
                    item.setExchangeRate(BigDecimal.valueOf(0.065));
                    item.setFee(BigDecimal.ZERO);
                    item.setPrice(BigDecimal.valueOf(500));
                    item.setQuantity(BigDecimal.ONE);

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
