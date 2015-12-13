package info.tongrenlu.service;

import org.springframework.beans.factory.annotation.Autowired;

import info.tongrenlu.domain.ShopBean;
import info.tongrenlu.manager.ShopManager;

public class AdminShopService {

    @Autowired
    private ShopManager shopManager = null;

    public ShopBean getDefaultShop() {
        return this.shopManager.getDefaultShop();
    }

    public void update(
                       final ShopBean shopBean) {
    }

    public void updateDefaultShop(
                                  final ShopBean shopBean) {
        this.shopManager.updateDefaultShop(shopBean);
    }

}
