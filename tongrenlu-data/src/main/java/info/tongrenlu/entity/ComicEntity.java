package info.tongrenlu.entity;

public class ComicEntity extends ArticleEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public ComicEntity(final String objectId, final String redFlg,
            final String translateFlg) {
        this.redFlg = redFlg;
        this.translateFlg = translateFlg;
    }

    public ComicEntity() {
    }

    private String redFlg;

    private String translateFlg;

    @Override
    public String getArticleId() {
        return this.getObjectId();
    }

    @Override
    public void setArticleId(final String articleId) {
        this.setObjectId(articleId);
    }

    public String getRedFlg() {
        return this.redFlg;
    }

    public void setRedFlg(final String redFlg) {
        this.redFlg = redFlg;
    }

    public String getTranslateFlg() {
        return this.translateFlg;
    }

    public void setTranslateFlg(final String translateFlg) {
        this.translateFlg = translateFlg;
    }

}
