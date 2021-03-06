package info.tongrenlu.mapper;

import info.tongrenlu.domain.OrderBean;

import java.util.List;
import java.util.Map;

public interface OrderMapper {

    public void insert(OrderBean orderBean);

    public int count(Map<String, Object> params);

    public List<OrderBean> search(Map<String, Object> params);

    public OrderBean fetchBean(Map<String, Object> params);

    public void update(OrderBean orderBean);

    public void updateStatus(OrderBean orderBean);

    public List<Map<String, Object>> fetchDashboard();

    public void delete(OrderBean orderBean);

    public List<OrderBean> fetchList(Map<String, Object> params);
}
