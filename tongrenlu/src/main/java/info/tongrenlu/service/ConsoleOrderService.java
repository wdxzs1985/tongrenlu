package info.tongrenlu.service;

import info.tongrenlu.domain.OrderBean;
import info.tongrenlu.domain.OrderItemBean;
import info.tongrenlu.domain.OrderPayBean;
import info.tongrenlu.domain.UserBean;
import info.tongrenlu.mail.MailModel;
import info.tongrenlu.mail.MailResolvor;
import info.tongrenlu.manager.OrderItemManager;
import info.tongrenlu.manager.OrderManager;
import info.tongrenlu.manager.OrderPayManager;
import info.tongrenlu.manager.ShopManager;
import info.tongrenlu.manager.UserManager;
import info.tongrenlu.support.PaginateSupport;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ConsoleOrderService {

    @Autowired
    private OrderManager orderManager = null;
    @Autowired
    private OrderItemManager orderItemManager = null;
    @Autowired
    private OrderPayManager orderPayManager = null;
    @Autowired
    private ShopManager shopManager = null;
    @Autowired
    private UserManager userManager = null;
    @Autowired
    private MailResolvor mailResolvor = null;
    @Autowired
    private MessageSource messageSource = null;

    public void searchOrder(final PaginateSupport<OrderBean> paginate) {
        final int itemCount = this.orderManager.countOrder(paginate.getParams());
        paginate.setItemCount(itemCount);
        paginate.compute();

        final List<OrderBean> items = this.orderManager.searchOrder(paginate.getParams());
        paginate.setItems(items);
    }

    public OrderBean findByOrderId(final Integer orderId) {
        return this.orderManager.findByOrderId(orderId);
    }

    public List<OrderItemBean> findItemList(final OrderBean orderBean) {
        return this.orderItemManager.findList(orderBean);
    }

    public void updateOrder(final OrderBean orderBean,
                            final List<OrderItemBean> itemList) {
        if (CollectionUtils.isEmpty(itemList)) {
            this.orderManager.delete(orderBean);
        } else {
            BigDecimal amountJp = BigDecimal.ZERO;
            BigDecimal amountCn = BigDecimal.ZERO;
            BigDecimal fee = BigDecimal.ZERO;
            BigDecimal total = BigDecimal.ZERO;
            BigDecimal quantity = BigDecimal.ZERO;
            BigDecimal shippingFee = BigDecimal.ZERO;

            for (final OrderItemBean item : itemList) {
                amountJp = amountJp.add(item.getAmountJp());
                amountCn = amountCn.add(item.getAmountCn());
                fee = fee.add(item.getTotalFee());
                total = total.add(item.getTotal());
                quantity = quantity.add(item.getQuantity());

                this.orderItemManager.update(item);
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
            default:
                break;
            }
            total = total.add(shippingFee);

            orderBean.setQuantity(quantity);
            orderBean.setAmountJp(amountJp);
            orderBean.setAmountCn(amountCn);
            orderBean.setFee(fee);
            orderBean.setShippingFee(shippingFee);
            orderBean.setTotal(total);

            this.orderManager.update(orderBean);
        }
    }

    public void updateOrderStatus(final OrderBean orderBean, final Locale locale) {
        this.orderManager.updateOrderStatus(orderBean);
        if (OrderBean.STATUS_CREATE == (orderBean.getStatus())) {
            this.orderItemManager.updateStatus(orderBean,
                                               OrderItemBean.STATUS_CREATE);
        } else if (OrderBean.STATUS_CANCEL == (orderBean.getStatus())) {
            this.orderItemManager.updateStatus(orderBean,
                                               OrderItemBean.STATUS_CANCEL);
            this.orderPayManager.updateStatus(orderBean,
                                              OrderItemBean.STATUS_CANCEL);
        } else if (OrderBean.STATUS_SEND_DIRECT == (orderBean.getStatus())) {
            this.orderItemManager.updateStatus(orderBean,
                                               OrderItemBean.STATUS_SEND_DIRECT);
        } else if (OrderBean.STATUS_SEND_GROUP == (orderBean.getStatus())) {
            this.orderItemManager.updateStatus(orderBean,
                                               OrderItemBean.STATUS_SEND_GROUP);
        } else if (OrderBean.STATUS_FINISH == (orderBean.getStatus())) {
            this.orderItemManager.updateStatus(orderBean,
                                               OrderItemBean.STATUS_FINISH);
        }

        final MailModel mailModel = this.mailResolvor.createMailModel(locale);
        final UserBean userBean = orderBean.getUserBean();
        final UserBean shopper = orderBean.getShopper();

        mailModel.addAttribute("orderBean", orderBean);
        mailModel.addAttribute("userBean", userBean);
        mailModel.setTo(this.mailResolvor.createAddress(userBean.getEmail(),
                                                        userBean.getNickname()));

        if (shopper != null) {
            mailModel.setBcc(this.mailResolvor.createAddress(shopper.getEmail(),
                                                             shopper.getNickname()));
            mailModel.addAttribute("shopper", shopper);
        }

        mailModel.setSubject(this.messageSource.getMessage("mail.order.subject",
                                                           new Object[] { userBean.getNickname() },
                                                           locale));
        switch (orderBean.getStatus()) {
        case OrderBean.STATUS_CREATE:
            mailModel.setTemplate("order_restore");
            mailModel.addAttribute("itemList",
                                   this.orderItemManager.findList(orderBean));
            break;
        case OrderBean.STATUS_START:
            mailModel.setTemplate("order_start");
            mailModel.addAttribute("itemList",
                                   this.orderItemManager.findList(orderBean));
            break;
        case OrderBean.STATUS_PAID:
            mailModel.setTemplate("order_paid");
            break;
        case OrderBean.STATUS_SEND_GROUP:
            mailModel.setTemplate("order_send_group");
            break;
        case OrderBean.STATUS_SEND_DIRECT:
            mailModel.setTemplate("order_send_direct");
            break;
        case OrderBean.STATUS_FINISH:
            mailModel.setTemplate("order_finish");
            break;
        case OrderBean.STATUS_CANCEL:
            mailModel.setTemplate("order_cancel");
            break;
        default:
            break;
        }
        this.mailResolvor.send(mailModel);
    }

    public void removeItem(final OrderBean orderBean, final Integer orderItemId) {
        this.orderItemManager.remove(orderItemId);
        final List<OrderItemBean> itemList = this.findItemList(orderBean);
        this.updateOrder(orderBean, itemList);
    }

    public List<OrderItemBean> findStockItemList(final UserBean shopperBean) {
        return this.orderItemManager.findStockList(shopperBean);
    }

    public void updateOrderItemStatus(final List<OrderItemBean> items) {
        for (final OrderItemBean item : items) {
            this.orderItemManager.updateStatus(item);
        }
    }

    public int countOrderByStatus(final Integer status) {
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("status", status);
        return this.orderManager.countOrder(params);
    }

    public Map<String, Integer> getDashboard(final UserBean loginUser) {
        final Map<String, Integer> dashboard = new HashMap<String, Integer>();
        final List<Map<String, Object>> results = this.orderManager.fetchDashboard();
        for (final Map<String, Object> map : results) {
            final int status = (Integer) map.get("status");
            final int count = ((Long) map.get("count")).intValue();

            switch (status) {
            case OrderBean.STATUS_CREATE:
                dashboard.put("createCount", count);
                break;
            case OrderBean.STATUS_START:
                dashboard.put("startCount", count);
                break;
            case OrderBean.STATUS_PAID:
                dashboard.put("paidCount", count);
                break;
            case OrderBean.STATUS_SEND_GROUP:
                dashboard.put("sendGroupCount", count);
                break;
            case OrderBean.STATUS_SEND_DIRECT:
                dashboard.put("sendDirectCount", count);
                break;
            default:
                break;
            }
        }
        return dashboard;
    }

    public void deleteOrder(final OrderBean orderBean) {
        this.orderManager.delete(orderBean);
    }

    public OrderBean mergeOrder(final Integer userId, final Locale locale) {
        final UserBean userBean = this.userManager.getById(userId);

        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("userBean", userBean);
        params.put("status",
                   new Integer[] { OrderBean.STATUS_CREATE, OrderBean.STATUS_START, OrderBean.STATUS_PAID });
        final List<OrderBean> orderList = this.orderManager.getList(params);
        if (CollectionUtils.size(orderList) > 1) {
            final List<OrderItemBean> newItemList = new ArrayList<OrderItemBean>();
            final List<OrderPayBean> newPayList = new ArrayList<OrderPayBean>();

            for (final OrderBean orderBean : orderList) {
                final List<OrderItemBean> itemList = this.orderItemManager.findList(orderBean);
                newItemList.addAll(itemList);

                final List<OrderPayBean> payList = this.orderPayManager.findList(orderBean);
                newPayList.addAll(payList);

                this.deleteOrder(orderBean);
            }

            final OrderBean orderBean = new OrderBean();
            final OrderItemBean firstItem = (OrderItemBean) CollectionUtils.get(newItemList,
                                                                                0);
            String title = firstItem.getTitle();

            if (CollectionUtils.size(newItemList) > 1) {
                title = this.messageSource.getMessage("order.title.etc",
                                                      new Object[] { title },
                                                      locale);
            }

            orderBean.setUserBean(userBean);
            orderBean.setTitle(title);

            BigDecimal amountJp = BigDecimal.ZERO;
            BigDecimal amountCn = BigDecimal.ZERO;
            BigDecimal fee = BigDecimal.ZERO;
            BigDecimal total = BigDecimal.ZERO;
            BigDecimal quantity = BigDecimal.ZERO;
            BigDecimal shippingFee = BigDecimal.ZERO;

            for (final OrderItemBean item : newItemList) {
                amountJp = amountJp.add(item.getAmountJp());
                amountCn = amountCn.add(item.getAmountCn());
                fee = fee.add(item.getTotalFee());
                total = total.add(item.getTotal());
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
            default:
                final int quantityInt = quantity.intValue();
                if (quantityInt > 15) {
                    orderBean.setShippingMethod(OrderBean.SHIPPING_SAL);
                    shippingFee = (this.getSalPrice(quantity));
                } else if (quantityInt >= 10) {
                    orderBean.setShippingMethod(OrderBean.SHIPPING_EMS);
                    shippingFee = (this.getEmsPrice(quantity));
                } else {
                    orderBean.setShippingMethod(OrderBean.SHIPPING_GROUP);
                    shippingFee = (this.getGroupPrice(quantity));
                }
                break;
            }
            total = total.add(shippingFee);

            orderBean.setQuantity(quantity);
            orderBean.setAmountJp(amountJp);
            orderBean.setAmountCn(amountCn);
            orderBean.setFee(fee);
            orderBean.setShippingFee(shippingFee);
            orderBean.setTotal(total);

            this.orderManager.insertOrder(orderBean);

            for (final OrderItemBean orderItemBean : newItemList) {
                orderItemBean.setOrderBean(orderBean);
                this.orderItemManager.update(orderItemBean);
            }
            for (final OrderPayBean orderPayBean : newPayList) {
                orderPayBean.setOrderBean(orderBean);
                this.orderPayManager.update(orderPayBean);
            }

            final MailModel mailModel = this.mailResolvor.createMailModel(locale);
            mailModel.setSubject(this.messageSource.getMessage("mail.order.subject",
                                                               new Object[] { userBean.getNickname() },
                                                               locale));
            mailModel.setTo(this.mailResolvor.createAddress(userBean.getEmail(),
                                                            userBean.getNickname()));
            mailModel.setTemplate("order_merge");

            mailModel.addAttribute("userBean", userBean);
            mailModel.addAttribute("orderBean", orderBean);
            mailModel.addAttribute("itemList", newItemList);

            this.mailResolvor.send(mailModel);

            return orderBean;
        }
        return null;
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

    public List<OrderPayBean> findPayList(final OrderBean orderBean) {
        return this.orderPayManager.findList(orderBean);
    }

    public void addPay(final OrderBean orderBean,
                       final OrderPayBean orderPayBean, final Locale locale) {

        this.orderPayManager.add(orderPayBean);

        final UserBean userBean = orderPayBean.getUserBean();

        final MailModel mailModel = this.mailResolvor.createMailModel(locale);
        mailModel.setSubject(this.messageSource.getMessage("mail.order.subject",
                                                           new Object[] { userBean.getNickname() },
                                                           locale));
        mailModel.setTo(this.mailResolvor.createAddress(userBean.getEmail(),
                                                        userBean.getNickname()));
        mailModel.setTemplate("order_pay");

        mailModel.addAttribute("userBean", userBean);
        mailModel.addAttribute("orderBean", orderBean);
        mailModel.addAttribute("orderPayBean", orderPayBean);

        this.mailResolvor.send(mailModel);
    }

    public void updatePayStatus(final OrderBean orderBean,
                                final OrderPayBean orderPayBean,
                                final Locale locale) {

        this.orderManager.updateOrderStatus(orderBean);

        this.orderPayManager.updateStatus(orderPayBean);

        final UserBean userBean = orderBean.getUserBean();

        final MailModel mailModel = this.mailResolvor.createMailModel(locale);
        mailModel.setSubject(this.messageSource.getMessage("mail.order.subject",
                                                           new Object[] { userBean.getNickname() },
                                                           locale));
        mailModel.setTo(this.mailResolvor.createAddress(userBean.getEmail(),
                                                        userBean.getNickname()));
        mailModel.setTemplate("order_paid");

        mailModel.addAttribute("userBean", userBean);
        mailModel.addAttribute("orderBean", orderBean);
        mailModel.addAttribute("orderPayBean", orderPayBean);

        this.mailResolvor.send(mailModel);
    }

    public void removePay(final Map<String, Object> model) {
        this.orderPayManager.remove(model);
    }
}
