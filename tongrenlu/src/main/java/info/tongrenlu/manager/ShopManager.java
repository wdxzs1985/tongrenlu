package info.tongrenlu.manager;

import info.tongrenlu.domain.ShopBean;
import info.tongrenlu.mapper.ShopMapper;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShopManager {

    public static final Integer DEFAULT_SHOP_ID = 0;

    @Autowired
    private ShopMapper shopMapper = null;

    public ShopBean getDefaultShop() {
        return this.getShop(DEFAULT_SHOP_ID);
    }

    public ShopBean getShop(final Integer shopId) {
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("shopId", shopId);
        return this.shopMapper.fetchBean(params);
    }
}