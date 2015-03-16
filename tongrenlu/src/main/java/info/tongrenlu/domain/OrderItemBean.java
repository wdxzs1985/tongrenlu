package info.tongrenlu.domain;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class OrderItemBean extends DtoBean {

    /**
     *
     */
    private static final long serialVersionUID = 4748907038743274705L;

    private UserBean userBean = null;

    private OrderBean orderBean = null;

    private String title = null;

    private String url = null;

    private String shop = null;

    private BigDecimal price = BigDecimal.ZERO;

    private BigDecimal quantity = BigDecimal.ZERO;

    private BigDecimal exchangeRate = BigDecimal.ZERO;

    private BigDecimal fee = BigDecimal.ZERO;

    @JsonIgnore
    private Integer status = 0;

    private Boolean removable = null;

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

    public BigDecimal getFee() {
        return this.fee;
    }

    public void setFee(final BigDecimal fee) {
        this.fee = fee;
    }

    public BigDecimal getAmountJp() {
        return this.price.multiply(this.quantity);
    }

    public BigDecimal getAmountCn() {
        return this.getAmountJp().multiply(this.exchangeRate);
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

    public Boolean getRemovable() {
        return this.removable;
    }

    public void setRemovable(final Boolean removable) {
        this.removable = removable;
    }
}