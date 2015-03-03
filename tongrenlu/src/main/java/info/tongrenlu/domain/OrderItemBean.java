package info.tongrenlu.domain;

import java.math.BigDecimal;

public class OrderItemBean extends DtoBean {

    private UserBean userBean = null;

    private OrderBean orderBean = null;

    private String title = null;

    private String url = null;

    private String shop = null;

    private BigDecimal price = BigDecimal.ZERO;

    private BigDecimal quantity = BigDecimal.ZERO;

    private BigDecimal exchangeRate = BigDecimal.ZERO;

    private BigDecimal taxRate = BigDecimal.ZERO;

    private BigDecimal fee = BigDecimal.ZERO;

    private Integer status = 0;

    public String getUrl() {
        return this.url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public String getShop() {
        return this.shop;
    }

    public void setShop(final String shop) {
        this.shop = shop;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(final BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getQuantity() {
        return this.quantity;
    }

    public void setQuantity(final BigDecimal quantity) {
        this.quantity = quantity;
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

    public BigDecimal getFee() {
        return this.fee;
    }

    public void setFee(final BigDecimal fee) {
        this.fee = fee;
    }

    public BigDecimal getAmountJp() {
        return this.price.multiply(this.quantity);
    }

    public BigDecimal getTax() {
        return this.getAmountJp().multiply(this.taxRate);

    }

    public BigDecimal getAmountCn() {
        final BigDecimal amountJp = this.getAmountJp();
        final BigDecimal tax = this.getTax();
        final BigDecimal totalJp = amountJp.add(tax);
        return totalJp.multiply(this.exchangeRate);
    }

    public BigDecimal getTotal() {
        return this.getAmountCn().add(this.fee);
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(final Integer status) {
        this.status = status;
    }

    public OrderBean getOrderBean() {
        return this.orderBean;
    }

    public void setOrderBean(final OrderBean orderBean) {
        this.orderBean = orderBean;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public UserBean getUserBean() {
        return this.userBean;
    }

    public void setUserBean(final UserBean userBean) {
        this.userBean = userBean;
    }
}
