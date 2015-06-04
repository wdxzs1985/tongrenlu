package info.tongrenlu.domain;

import java.math.BigDecimal;
import java.util.Date;

public class OrderBean extends DtoBean {

    public static final int STATUS_CREATE = 0;
    public static final int STATUS_START = 1;
    public static final int STATUS_PAID = 2;
    public static final int STATUS_SEND_GROUP = 3;
    public static final int STATUS_SEND_DIRECT = 4;
    public static final int STATUS_FINISH = 5;
    public static final int STATUS_CANCEL = 9;

    public static final int SHIPPING_EMS = 3;
    public static final int SHIPPING_SAL = 2;
    public static final int SHIPPING_GROUP = 1;
    public static final int SHIPPING_UNKNOWN = 0;

    private UserBean userBean = null;

    private UserBean shopper = null;

    private String title = null;

    private BigDecimal amountJp = BigDecimal.ZERO;

    private BigDecimal amountCn = BigDecimal.ZERO;

    private BigDecimal fee = BigDecimal.ZERO;

    private BigDecimal total = BigDecimal.ZERO;

    private BigDecimal shippingFee = BigDecimal.ZERO;

    private Date createDate = null;

    private Date orderDate = null;

    private Date payDate = null;

    private Date sendDate = null;

    private Date receiveDate = null;

    private Date cancelDate = null;

    private String payLink = null;

    private String payNo = null;

    private String trackingCode = null;

    private Integer status = STATUS_CREATE;

    private Integer shippingMethod = SHIPPING_UNKNOWN;

    private BigDecimal quantity = BigDecimal.ZERO;

    public UserBean getUserBean() {
        return this.userBean;
    }

    public void setUserBean(final UserBean userBean) {
        this.userBean = userBean;
    }

    public BigDecimal getAmountJp() {
        return this.amountJp;
    }

    public void setAmountJp(final BigDecimal amountJp) {
        this.amountJp = amountJp;
    }

    public BigDecimal getAmountCn() {
        return this.amountCn;
    }

    public void setAmountCn(final BigDecimal amountCn) {
        this.amountCn = amountCn;
    }

    public BigDecimal getFee() {
        return this.fee;
    }

    public void setFee(final BigDecimal fee) {
        this.fee = fee;
    }

    public BigDecimal getTotal() {
        return this.total;
    }

    public void setTotal(final BigDecimal total) {
        this.total = total;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(final Date createDate) {
        this.createDate = createDate;
    }

    public Date getOrderDate() {
        return this.orderDate;
    }

    public void setOrderDate(final Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getPayDate() {
        return this.payDate;
    }

    public void setPayDate(final Date payDate) {
        this.payDate = payDate;
    }

    public Date getSendDate() {
        return this.sendDate;
    }

    public void setSendDate(final Date sendDate) {
        this.sendDate = sendDate;
    }

    public Date getReceiveDate() {
        return this.receiveDate;
    }

    public void setReceiveDate(final Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(final Integer status) {
        this.status = status;
    }

    public Date getCancelDate() {
        return this.cancelDate;
    }

    public void setCancelDate(final Date cancelDate) {
        this.cancelDate = cancelDate;
    }

    public String getTrackingCode() {
        return this.trackingCode;
    }

    public void setTrackingCode(final String trackingCode) {
        this.trackingCode = trackingCode;
    }

    public UserBean getShopper() {
        return this.shopper;
    }

    public void setShopper(final UserBean shopper) {
        this.shopper = shopper;
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

    public BigDecimal getQuantity() {
        return this.quantity;
    }

    public void setQuantity(final BigDecimal quantity) {
        this.quantity = quantity;
    }

    public Integer getShippingMethod() {
        return this.shippingMethod;
    }

    public void setShippingMethod(final Integer shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public BigDecimal getShippingFee() {
        return this.shippingFee;
    }

    public void setShippingFee(final BigDecimal shippingFee) {
        this.shippingFee = shippingFee;
    }

}
