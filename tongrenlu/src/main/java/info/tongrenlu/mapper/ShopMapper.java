package info.tongrenlu.mapper;

import info.tongrenlu.domain.ShopBean;

import java.util.Map;

public interface ShopMapper {

    public ShopBean fetchBean(Map<String, Object> params);

}
