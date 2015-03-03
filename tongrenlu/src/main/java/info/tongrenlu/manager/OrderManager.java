package info.tongrenlu.manager;

import info.tongrenlu.domain.OrderBean;
import info.tongrenlu.domain.OrderItemBean;
import info.tongrenlu.mapper.OrderItemMapper;
import info.tongrenlu.mapper.OrderMapper;

import java.util.Collection;

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
}
