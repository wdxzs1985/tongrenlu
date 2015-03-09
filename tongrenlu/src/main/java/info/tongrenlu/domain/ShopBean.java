package info.tongrenlu.domain;

import java.math.BigDecimal;

public class ShopBean extends DtoBean {

    private UserBean userBean;

    private String shopName;

    private BigDecimal exchangeRate;

    private BigDecimal fee;

    public UserBean getUserBean() {
        return this.userBean;
    }

    public void setUserBean(final UserBean userBean) {
        this.userBean = userBean;
    }

    public String getShopName() {
        return this.shopName;
    }

    public void setShopName(final String shopName) {
        this.shopName = shopName;
    }

    public BigDecimal getExchangeRate() {
        return this.exchangeRate;
    }

    public void setExchangeRate(final BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public BigDecimal getFee() {
        return this.fee;
    }

    public void setFee(final BigDecimal fee) {
        this.fee = fee;
    }

}
