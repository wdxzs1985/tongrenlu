package info.tongrenlu.entity;

public class FileEntity extends DtoBean {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public FileEntity() {
    }

    private String name;

    private String extension;

    private String articleId;

    private Integer orderNo;

    private ArticleEntity article;

    private String contentType;

    public String getFileId() {
        return this.getObjectId();
    }

    public FileEntity(final Integer id, final String objectId, final String name, final String extension, final String articleId, final Integer orderNo) {
        super(id, objectId);
        this.name = name;
        this.extension = extension;
        this.articleId = articleId;
        this.orderNo = orderNo;
    }

    public void setFileId(final String fileId) {
        this.setObjectId(fileId);
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getExtension() {
        return this.extension;
    }

    public void setExtension(final String extension) {
        this.extension = extension;
    }

    public String getArticleId() {
        return this.articleId;
    }

    public void setArticleId(final String articleId) {
        this.articleId = articleId;
    }

    public Integer getOrderNo() {
        return this.orderNo;
    }

    public void setOrderNo(final Integer orderNo) {
        this.orderNo = orderNo;
    }

    public ArticleEntity getArticle() {
        return this.article;
    }

    public void setArticle(final ArticleEntity article) {
        this.article = article;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

}
