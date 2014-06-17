package info.tongrenlu.domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class ArticleTagBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 8410195215169913699L;

    private TagBean tagBean = null;

    private ArticleBean articleBean = null;

    public TagBean getTagBean() {
        return this.tagBean;
    }

    public void setTagBean(final TagBean tagBean) {
        this.tagBean = tagBean;
    }

    public ArticleBean getArticleBean() {
        return this.articleBean;
    }

    public void setArticleBean(final ArticleBean articleBean) {
        this.articleBean = articleBean;
    }
}
