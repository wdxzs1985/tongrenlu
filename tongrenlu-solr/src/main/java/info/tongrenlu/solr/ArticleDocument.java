package info.tongrenlu.solr;

import java.io.Serializable;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.SolrDocument;

@SolrDocument(solrCoreName = "collection3")
public class ArticleDocument implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Field
    private String id;

    @Field
    private String category;

    @Field
    private Integer articleId;

    @Field
    private String title;

    @Field
    private String description;

    @Field
    private String[] tags;

    @Field
    private Integer fileId;

    @Field
    private String track;

    @Field
    private String[] artist;

    @Field
    private String[] original;

    @Field
    private Boolean instrumental = false;

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

    public Integer getFileId() {
        return this.fileId;
    }

    public void setFileId(final Integer fileId) {
        this.fileId = fileId;
    }

    public String getTrack() {
        return this.track;
    }

    public void setTrack(final String track) {
        this.track = track;
    }

    public Boolean getInstrumental() {
        return this.instrumental;
    }

    public void setInstrumental(final Boolean instrumental) {
        this.instrumental = instrumental;
    }

    public String getId() {
        return this.id;
    }

    public String[] getTags() {
        return this.tags;
    }

    public void setTags(final String[] tags) {
        this.tags = tags;
    }

    public String[] getArtist() {
        return this.artist;
    }

    public void setArtist(final String[] artist) {
        this.artist = artist;
    }

    public String[] getOriginal() {
        return this.original;
    }

    public void setOriginal(final String[] original) {
        this.original = original;
    }
}
