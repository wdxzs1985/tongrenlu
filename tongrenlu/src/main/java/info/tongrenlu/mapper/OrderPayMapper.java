package info.tongrenlu.mapper;

import info.tongrenlu.domain.OrderPayBean;

import java.util.List;
import java.util.Map;

public interface OrderPayMapper {

    public void insert(OrderPayBean orderPayBean);

    public List<OrderPayBean> fetchList(Map<String, Object> params);

    public void updateStatus(Map<String, Object> params);

    public void delete(Map<String, Object> params);

    public void update(OrderPayBean orderPayBean);
}
