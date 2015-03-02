package info.tongrenlu.domain;

import java.math.BigDecimal;

public class OrderItemBean extends DtoBean {

    private String name = null;

    private String url = null;

    private String shop = null;

    private BigDecimal price = BigDecimal.ZERO;

    private BigDecimal quantity = BigDecimal.ZERO;

    private BigDecimal exchangeRate = BigDecimal.ZERO;

    private BigDecimal taxRate = BigDecimal.ZERO;

    private BigDecimal fee = BigDecimal.ZERO;

    private Integer status = 0;

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

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
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
