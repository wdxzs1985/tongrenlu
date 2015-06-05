package info.tongrenlu.manager;

import info.tongrenlu.domain.OrderBean;
import info.tongrenlu.mapper.EmsMapper;
import info.tongrenlu.mapper.OrderMapper;
import info.tongrenlu.mapper.SalMapper;

import java.math.BigDecimal;
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
    private EmsMapper emsMapper = null;

    @Autowired
    private SalMapper salMapper = null;

    public void insertOrder(final OrderBean orderBean) {
        this.orderMapper.insert(orderBean);
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

    public void update(final OrderBean orderBean) {
        this.orderMapper.update(orderBean);
    }

    public void updateOrderStatus(final OrderBean orderBean) {
        this.orderMapper.updateStatus(orderBean);
    }

    public List<Map<String, Object>> fetchDashboard() {
        return this.orderMapper.fetchDashboard();
    }

    public void delete(final OrderBean orderBean) {
        this.orderMapper.delete(orderBean);
    }

    public List<OrderBean> getList(final Map<String, Object> params) {
        return this.orderMapper.fetchList(params);
    }

    public Integer getEmsPrice(final BigDecimal quantity) {
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("quantity", quantity);
        return this.emsMapper.fetchPrice(params);
    }

    public Integer getSalPrice(final BigDecimal quantity) {
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("quantity", quantity);
        return this.salMapper.fetchPrice(params);
    }

}
