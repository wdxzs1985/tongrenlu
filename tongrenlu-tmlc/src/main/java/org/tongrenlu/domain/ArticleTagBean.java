package org.tongrenlu.domain;

public class ArticleTagBean extends DtoBean {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

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
