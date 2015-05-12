package info.tongrenlu.service;

import info.tongrenlu.domain.OrderBean;
import info.tongrenlu.domain.OrderItemBean;
import info.tongrenlu.domain.UserBean;
import info.tongrenlu.mail.MailModel;
import info.tongrenlu.mail.MailResolvor;
import info.tongrenlu.manager.OrderManager;
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
        return this.orderManager.findItemList(orderBean);
    }

    public void updateOrder(final OrderBean orderBean, final List<OrderItemBean> itemList) {
        if (CollectionUtils.isEmpty(itemList)) {
            this.orderManager.delete(orderBean);
        } else {
            BigDecimal amountJp = BigDecimal.ZERO;
            BigDecimal amountCn = BigDecimal.ZERO;
            BigDecimal fee = BigDecimal.ZERO;
            BigDecimal total = BigDecimal.ZERO;

            for (final OrderItemBean item : itemList) {
                amountJp = amountJp.add(item.getAmountJp());
                amountCn = amountCn.add(item.getAmountCn());
                fee = fee.add(item.getFee());
                total = total.add(item.getTotal());
                this.orderManager.updateOrderItem(item);
            }
            orderBean.setAmountJp(amountJp);
            orderBean.setAmountCn(amountCn);
            orderBean.setFee(fee);
            orderBean.setTotal(total);

            this.orderManager.update(orderBean);
        }
    }

    public void updateOrderStatus(final OrderBean orderBean, final Locale locale) {
        this.orderManager.updateOrderStatus(orderBean);
        if (OrderBean.STATUS_CANCEL == (orderBean.getStatus())) {
            this.orderManager.cancelOrderItem(orderBean);
        }

        final MailModel mailModel = this.mailResolvor.createMailModel(locale);
        final UserBean userBean = orderBean.getUserBean();
        final UserBean shopper = orderBean.getShopper();

        mailModel.addAttribute("orderBean", orderBean);
        mailModel.addAttribute("userBean", userBean);
        mailModel.setTo(this.mailResolvor.createAddress(userBean.getEmail(), userBean.getNickname()));

        if (shopper != null) {
            mailModel.setBcc(this.mailResolvor.createAddress(shopper.getEmail(), shopper.getNickname()));
            mailModel.addAttribute("shopper", shopper);
        }

        mailModel.setSubject(this.messageSource.getMessage("mail.order.subject",
                                                           new Object[] { userBean.getNickname() },
                                                           locale));
        switch (orderBean.getStatus()) {
        case OrderBean.STATUS_START:
            mailModel.setTemplate("order_start");
            final List<OrderItemBean> itemList = this.orderManager.findItemList(orderBean);
            mailModel.addAttribute("itemList", itemList);
            break;
        case OrderBean.STATUS_PAID:
            mailModel.setTemplate("order_paid");
            break;
        case OrderBean.STATUS_SEND:
            mailModel.setTemplate("order_send");
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
        this.orderManager.removeItem(orderItemId);
        final List<OrderItemBean> itemList = this.findItemList(orderBean);
        this.updateOrder(orderBean, itemList);
    }

    public List<OrderItemBean> findStockItemList(final UserBean shopperBean) {
        return this.orderManager.findStockItemList(shopperBean);
    }

    public void updateOrderItemStatus(final OrderItemBean item) {
        this.orderManager.updateOrderItemStatus(item);
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
            case OrderBean.STATUS_SEND:
                dashboard.put("sendCount", count);
                break;
            default:
                break;
            }
        }
        return dashboard;
    }

    public void deleteOrder(final OrderBean orderBean) {
        this.orderManager.delete(orderBean);
        this.orderManager.cancelOrderItem(orderBean);
    }

    public void mergeOrder(final Integer userId, final Locale locale) {

        final UserBean userBean = this.userManager.getById(userId);

        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("userBean", userBean);
        params.put("status", OrderBean.STATUS_CREATE);
        final List<OrderBean> orderList = this.orderManager.getList(params);
        if (CollectionUtils.size(orderList) > 1) {
            final List<OrderItemBean> newItemList = new ArrayList<OrderItemBean>();

            for (final OrderBean orderBean : orderList) {
                final List<OrderItemBean> itemList = this.orderManager.findItemList(orderBean);
                newItemList.addAll(itemList);
                this.deleteOrder(orderBean);
            }

            if (CollectionUtils.isNotEmpty(newItemList)) {
                final OrderBean orderBean = new OrderBean();
                final OrderItemBean firstItem = (OrderItemBean) CollectionUtils.get(newItemList, 0);
                String title = firstItem.getTitle();

                if (CollectionUtils.size(newItemList) > 1) {
                    title = this.messageSource.getMessage("order.title.etc", new Object[] { title }, locale);
                }

                orderBean.setUserBean(userBean);
                orderBean.setTitle(title);

                BigDecimal amountJp = BigDecimal.ZERO;
                BigDecimal amountCn = BigDecimal.ZERO;
                BigDecimal fee = BigDecimal.ZERO;
                BigDecimal total = BigDecimal.ZERO;

                for (final OrderItemBean item : newItemList) {
                    amountJp = amountJp.add(item.getAmountJp());
                    amountCn = amountCn.add(item.getAmountCn());
                    fee = fee.add(item.getFee());
                    total = total.add(item.getTotal());

                    item.setOrderBean(orderBean);
                }

                orderBean.setAmountJp(amountJp);
                orderBean.setAmountCn(amountCn);
                orderBean.setFee(fee);
                orderBean.setTotal(total);

                this.orderManager.insertOrder(orderBean);
                this.orderManager.insertOrderItems(newItemList);

                final MailModel mailModel = this.mailResolvor.createMailModel(locale);
                mailModel.setSubject(this.messageSource.getMessage("mail.order.subject",
                                                                   new Object[] { userBean.getNickname() },
                                                                   locale));
                mailModel.setTo(this.mailResolvor.createAddress(userBean.getEmail(), userBean.getNickname()));
                mailModel.setTemplate("order_merge");

                mailModel.addAttribute("userBean", userBean);
                mailModel.addAttribute("orderBean", orderBean);
                mailModel.addAttribute("itemList", newItemList);

                this.mailResolvor.send(mailModel);

            }
        }
    }
}
