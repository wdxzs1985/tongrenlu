package info.tongrenlu.manager;

import info.tongrenlu.domain.OrderBean;
import info.tongrenlu.domain.OrderPayBean;
import info.tongrenlu.mapper.OrderPayMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderPayManager {

    @Autowired
    private OrderPayMapper orderPayMapper = null;

    public List<OrderPayBean> findList(final OrderBean orderBean) {
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("orderBean", orderBean);
        return this.orderPayMapper.fetchList(params);
    }

    public void add(final OrderPayBean orderPayBean) {
        this.orderPayMapper.insert(orderPayBean);
    }

    public void update(final OrderPayBean orderPayBean) {
        this.orderPayMapper.update(orderPayBean);
    }

    public void updateStatus(final Map<String, Object> params) {
        this.orderPayMapper.updateStatus(params);
    }

    public void remove(final Map<String, Object> params) {
        this.orderPayMapper.delete(params);
    }

    public void updateStatus(final OrderBean orderBean, final Integer status) {
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("orderId", orderBean.getId());
        params.put("status", status);
        this.orderPayMapper.updateStatus(params);
    }

}
