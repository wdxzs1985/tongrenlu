package info.tongrenlu.domain;

import java.math.BigDecimal;
import java.util.Date;

public class OrderPayBean extends DtoBean {

    public static final int STATUS_CREATE = 0;
    public static final int STATUS_PAID = 1;

    private UserBean userBean = null;

    private OrderBean orderBean = null;

    private String title = null;

    private BigDecimal amount = BigDecimal.ZERO;

    private Date createDate = null;

    private Date payDate = null;

    private String payLink = null;

    private String payNo = null;

    private Integer status = null;

    public UserBean getUserBean() {
        return this.userBean;
    }

    public void setUserBean(final UserBean userBean) {
        this.userBean = userBean;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public void setAmount(final BigDecimal amount) {
        this.amount = amount;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(final Date createDate) {
        this.createDate = createDate;
    }

    public Date getPayDate() {
        return this.payDate;
    }

    public void setPayDate(final Date payDate) {
        this.payDate = payDate;
    }

    public String getPayLink() {
        return this.payLink;
    }

    public void setPayLink(final String payLink) {
        this.payLink = payLink;
    }

    public String getPayNo() {
        return this.payNo;
    }

    public void setPayNo(final String payNo) {
        this.payNo = payNo;
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

}
