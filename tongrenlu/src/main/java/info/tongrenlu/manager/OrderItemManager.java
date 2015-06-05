package info.tongrenlu.manager;

import info.tongrenlu.domain.OrderBean;
import info.tongrenlu.domain.OrderItemBean;
import info.tongrenlu.domain.UserBean;
import info.tongrenlu.mapper.OrderItemMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderItemManager {

    @Autowired
    private OrderItemMapper orderItemMapper = null;

    public List<OrderItemBean> findList(final OrderBean orderBean) {
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("orderBean", orderBean);
        return this.orderItemMapper.fetchList(params);
    }

    public void insert(final OrderItemBean orderItemBean) {
        this.orderItemMapper.insert(orderItemBean);
    }

    public void update(final OrderItemBean orderItemBean) {
        this.orderItemMapper.update(orderItemBean);
    }

    public void remove(final Integer orderItemId) {
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("orderItemId", orderItemId);
        this.orderItemMapper.delete(params);
    }

    public List<OrderItemBean> findStockList(final UserBean shopperBean) {
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("shopperBean", shopperBean);
        return this.orderItemMapper.findStockItemList(params);
    }

    public void updateStatus(final OrderItemBean item) {
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("orderItemId", item.getId());
        params.put("status", item.getStatus());
        this.orderItemMapper.updateStatus(params);
    }

    public void updateStatus(final OrderBean orderBean, final Integer status) {
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("orderId", orderBean.getId());
        params.put("status", status);
        this.orderItemMapper.updateStatus(params);
    }

}
