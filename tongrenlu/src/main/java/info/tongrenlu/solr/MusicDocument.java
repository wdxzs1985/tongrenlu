package info.tongrenlu.solr;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.SolrDocument;

@SolrDocument(solrCoreName = "collection3")
public class MusicDocument {

    @Id
    @Field
    private String id;

    @Field
    private String category = "music";

    @Field
    private Integer articleId;

    @Field
    private String title;

    @Field
    private String description;

    @Field("tags")
    private String[] tags;

    public String getId() {
        return this.id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(final String category) {
        this.category = category;
    }

    public Integer getArticleId() {
        return this.articleId;
    }

    public void setArticleId(final Integer articleId) {
        this.articleId = articleId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String[] getTags() {
        return this.tags;
    }

    public void setTags(final String[] tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "ArticleBean" + " ["
               + "articleId="
               + this.articleId
               + ", title="
               + this.title
               + ", description="
               + this.description
               + "]";
    }
}
