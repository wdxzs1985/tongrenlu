package info.tongrenlu.mapper;

import info.tongrenlu.domain.OrderItemBean;

import java.util.List;
import java.util.Map;

public interface OrderItemMapper {

    public void insert(OrderItemBean orderItemBean);

    public List<OrderItemBean> fetchList(Map<String, Object> params);

    public void update(OrderItemBean orderItemBean);

    public void delete(Map<String, Object> params);

    public List<OrderItemBean> findStockItemList();

    public void updateStatus(OrderItemBean orderItemBean);

}
