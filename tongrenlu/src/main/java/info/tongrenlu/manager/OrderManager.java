package info.tongrenlu.manager;

import info.tongrenlu.domain.OrderBean;
import info.tongrenlu.domain.OrderItemBean;
import info.tongrenlu.mapper.OrderItemMapper;
import info.tongrenlu.mapper.OrderMapper;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderManager {

    @Autowired
    private OrderMapper orderMapper = null;

    @Autowired
    private OrderItemMapper orderItemMapper = null;

    public void insertOrder(final OrderBean orderBean) {
        this.orderMapper.insert(orderBean);
    }

    public void insertOrderItems(final Collection<OrderItemBean> itemList) {
        for (final OrderItemBean orderItemBean : itemList) {
            this.orderItemMapper.insert(orderItemBean);
        }
    }

    public int countOrder(final Map<String, Object> params) {
        return this.orderMapper.count(params);
    }

    public List<OrderBean> searchOrder(final Map<String, Object> params) {
        return this.orderMapper.search(params);
    }

    public OrderBean findByOrderId(final Integer orderId) {
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("orderId", orderId);
        return this.orderMapper.fetchBean(params);
    }

    public List<OrderItemBean> findItemList(final OrderBean orderBean) {
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("orderBean", orderBean);
        return this.orderItemMapper.fetchList(params);
    }

    public void update(final OrderBean orderBean) {
        this.orderMapper.update(orderBean);
    }

    public void updateOrderItem(final OrderItemBean orderItemBean) {
        this.orderItemMapper.update(orderItemBean);
    }

    public void updateOrderStatus(final OrderBean orderBean) {
        this.orderMapper.updateStatus(orderBean);
    }

    public void removeItem(final Integer orderItemId) {
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("orderItemId", orderItemId);
        this.orderItemMapper.delete(params);
    }

    public List<OrderItemBean> findStockItemList() {
        return this.orderItemMapper.findStockItemList();
    }

    public void updateOrderItemStatus(final OrderItemBean item) {
        this.orderItemMapper.updateStatus(item);
    }
}
