package info.tongrenlu.domain;

import java.math.BigDecimal;

public class ShopBean extends DtoBean {

    private UserBean userBean;

    private String shopName;

    private BigDecimal taxRate;

    private BigDecimal exchangeRate;

    private BigDecimal feeMailorder;

    private BigDecimal feeEvent;

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

    public BigDecimal getTaxRate() {
        return this.taxRate;
    }

    public void setTaxRate(final BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public BigDecimal getFeeMailorder() {
        return this.feeMailorder;
    }

    public void setFeeMailorder(final BigDecimal feeMailorder) {
        this.feeMailorder = feeMailorder;
    }

    public BigDecimal getFeeEvent() {
        return this.feeEvent;
    }

    public void setFeeEvent(final BigDecimal feeEvent) {
        this.feeEvent = feeEvent;
    }

}
