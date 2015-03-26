package info.tongrenlu.service;

import info.tongrenlu.domain.OrderBean;
import info.tongrenlu.domain.OrderItemBean;
import info.tongrenlu.domain.UserBean;
import info.tongrenlu.manager.OrderManager;
import info.tongrenlu.support.PaginateSupport;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
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

    public void updateOrder(final OrderBean orderBean, final List<OrderItemBean> itemList) {
        if (CollectionUtils.isEmpty(itemList)) {
            orderBean.setStatus(OrderBean.STATUS_CANCEL);
            this.orderManager.updateOrderStatus(orderBean);
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

    public void updateOrderStatus(final OrderBean orderBean) {
        this.orderManager.updateOrderStatus(orderBean);
    }

    public void removeItem(final OrderBean orderBean, final Integer orderItemId) {
        this.orderManager.removeItem(orderItemId);
        final List<OrderItemBean> itemList = this.findItemList(orderBean);
        this.updateOrder(orderBean, itemList);
    }

    public List<OrderItemBean> findStockItemList() {
        return this.orderManager.findStockItemList();
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
            final Integer status = (Integer) map.get("status");
            final Integer count = ((Long) map.get("count")).intValue();

            if (OrderBean.STATUS_CREATE.equals(status)) {
                dashboard.put("createCount", count);
            }
            if (OrderBean.STATUS_START.equals(status)) {
                dashboard.put("startCount", count);
            }
            if (OrderBean.STATUS_PAY.equals(status)) {
                dashboard.put("payCount", count);
            }
            if (OrderBean.STATUS_SEND.equals(status)) {
                dashboard.put("sendCount", count);
            }
        }
        return dashboard;
    }
}
