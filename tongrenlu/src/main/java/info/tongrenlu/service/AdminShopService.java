package info.tongrenlu.service;

import info.tongrenlu.domain.ShopBean;
import info.tongrenlu.manager.ShopManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminShopService {

    @Autowired
    private ShopManager shopManager = null;

    public ShopBean getDefaultShop() {
        return this.shopManager.getDefaultShop();
    }

    public void update(final ShopBean shopBean) {
    }

    public void updateDefaultShop(final ShopBean shopBean) {
        this.shopManager.updateDefaultShop(shopBean);
    }

}
