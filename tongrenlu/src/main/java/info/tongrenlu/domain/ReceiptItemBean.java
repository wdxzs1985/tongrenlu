package info.tongrenlu.domain;

import java.math.BigDecimal;

public class ReceiptItemBean extends DtoBean {

    private ArticleBean articleBean;

    private BigDecimal quantity;

    public ArticleBean getArticleBean() {
        return this.articleBean;
    }

    public void setArticleBean(final ArticleBean articleBean) {
        this.articleBean = articleBean;
    }

    public BigDecimal getQuantity() {
        return this.quantity;
    }

    public void setQuantity(final BigDecimal quantity) {
        this.quantity = quantity;
    }
}
