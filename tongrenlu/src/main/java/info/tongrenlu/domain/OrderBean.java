package info.tongrenlu.domain;

import java.math.BigDecimal;

public class OrderBean extends DtoBean {

    private UserBean userBean = null;

    private BigDecimal amountJp = BigDecimal.ZERO;

    private BigDecimal tax = BigDecimal.ZERO;

    private BigDecimal amountCn = BigDecimal.ZERO;

    private BigDecimal fee = BigDecimal.ZERO;

    private BigDecimal total = BigDecimal.ZERO;

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

    public BigDecimal getTax() {
        return this.tax;
    }

    public void setTax(final BigDecimal tax) {
        this.tax = tax;
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

}
