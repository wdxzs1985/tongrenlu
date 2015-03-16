package info.tongrenlu.service;

import info.tongrenlu.domain.OrderBean;
import info.tongrenlu.domain.OrderItemBean;
import info.tongrenlu.manager.OrderManager;
import info.tongrenlu.support.PaginateSupport;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ConsoleOrderService {

    @Autowired
    private OrderManager orderManager = null;

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

    public void updateOrderItem(final List<OrderItemBean> itemList) {

    }

    public void updateOrder(final OrderBean orderBean, final List<OrderItemBean> itemList) {

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

    public void startOrder(final OrderBean orderBean) {
        orderBean.setStatus(OrderBean.STATUS_START);
        this.orderManager.updateOrderStatus(orderBean);
    }

    public void finishOrder(final OrderBean orderBean) {
        orderBean.setStatus(OrderBean.STATUS_FINISH);
        this.orderManager.updateOrderStatus(orderBean);
    }

    public void cancelOrder(final OrderBean orderBean) {
        orderBean.setStatus(OrderBean.STATUS_CANCEL);
        this.orderManager.updateOrderStatus(orderBean);
    }
}