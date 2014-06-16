package info.tongrenlu.solr;

import info.tongrenlu.ArticleBean;

import java.util.Collection;
import java.util.List;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.SolrDocument;

@SolrDocument(solrCoreName = "collection3")
public class ArticleDocument {

    @Id
    @Field
    private String id;

    @Field
    private final String category = "music";

    @Field
    private String articleId;

    @Field
    private String title;

    @Field("tag")
    private Collection<String> tagList;

    @Field
    private Integer release;

    @Field
    private String event;

    @Field("circle")
    private Collection<String> circleList;

    public String getId() {
        return this.id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getArticleId() {
        return this.articleId;
    }

    public void setArticleId(final String articleId) {
        this.articleId = articleId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public Collection<String> getTagList() {
        return this.tagList;
    }

    public void setTagList(final List<String> tagList) {
        this.tagList = tagList;
    }

    public Integer getRelease() {
        return this.release;
    }

    public void setRelease(final Integer release) {
        this.release = release;
    }

    public String getEvent() {
        return this.event;
    }

    public void setEvent(final String event) {
        this.event = event;
    }

    public Collection<String> getCircleList() {
        return this.circleList;
    }

    public void setCircleList(final Collection<String> circleList) {
        this.circleList = circleList;
    }

    @Override
    public String toString() {
        return "ArticleBean [articleId=" + this.articleId
                + ", title="
                + this.title
                + ", tagList="
                + this.tagList
                + ", release="
                + this.release
                + ", event="
                + this.event
                + ", circleList="
                + this.circleList
                + "]";
    }

    public ArticleBean toArticleBean() {
        final ArticleBean articleBean = new ArticleBean();
        articleBean.setArticleId(this.getArticleId());
        articleBean.setTitle(this.getTitle());
        articleBean.setCircleList(this.getCircleList());
        return articleBean;
    }

    public String getCategory() {
        return this.category;
    }

}
