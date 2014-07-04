package info.tongrenlu.service;

import info.tongrenlu.entity.ArticleEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.http.MethodNotSupportedException;
import org.springframework.stereotype.Component;

@Component
public class ArticleRepository extends RepositorySupport implements Repository<ArticleEntity, Integer> {

    @Override
    public List<ArticleEntity> findAll() {
        try {
            final String sql = "Select " + "ARTICLE_ID      as articleId"
                    + "    ,   TITLE                  as title"
                    + "    ,   DESCRIPTION                 as description"
                    + "    ,   USER_ID                 as userId"
                    + "    ,   PUBLISH_FLG                   as publishFlg"
                    + "    ,   PUBLISH_DATE             as publishDate"
                    + "        from M_ARTICLE "
                    + "         where DEL_FLG = '0'";
            return this.getOracleDao().queryForList(sql, ArticleEntity.class);
        } catch (final Exception e) {
            return this.dummy();
        }

    }

    private List<ArticleEntity> dummy() {
        final List<ArticleEntity> dummy = new ArrayList<ArticleEntity>();
        dummy.add(new ArticleEntity(0,
                              "201407011270174",
                              "[SWING HOLIC] SWING HOLIC VOL.13",
                              "JAZZ风黑历史，TR.5大好评~\r\n(封面是教授",
                              "201304031052404",
                              "1",
                              new Date(),
                              "0"));
        dummy.add(new ArticleEntity(0,
                              "201406301269970",
                              "[永久立体] 赤き征裁",
                              "DESCRIPTION",
                              "201304031052404",
                              "1",
                              new Date(),
                              "0"));
        dummy.add(new ArticleEntity(0,
                              "201406291269769",
                              "[街角麻婆豆] 天狗レボリューション",
                              "麻婆豆终于来啦",
                              "201304031052404",
                              "1",
                              new Date(),
                              "0"));
        dummy.add(new ArticleEntity(0,
                              "201406281269170",
                              "[時遊戯画] 時悠響曲集",
                              "感受8-bit的魅力",
                              "201304031052404",
                              "1",
                              new Date(),
                              "0"));
        dummy.add(new ArticleEntity(0,
                              "201406281268773",
                              "[Crazy Berry] くるいちご三ルク",
                              "这乱来的CP。",
                              "201304031052404",
                              "1",
                              new Date(),
                              "0"));
        dummy.add(new ArticleEntity(0,
                              "201406281268770",
                              "[MN-logic24] メクルメクドリーミン",
                              "DESCRIPTION",// DESCRIPTION
                              "201304031052404",
                              "1",
                              new Date(),
                              "0"));
        dummy.add(new ArticleEntity(0,
                              "201406251268174",
                              "[アールグレイ] さとりとこいしのDOKIDOKIDONディスクEX!",
                              "DESCRIPTION",// DESCRIPTION
                              "201304031052404",
                              "1",
                              new Date(),
                              "0"));
        dummy.add(new ArticleEntity(0,
                              "201406241267979",
                              "[ジェリコの法則] Primitive Ignition",
                              "DESCRIPTION",// DESCRIPTION
                              "201212021000021",
                              "1",
                              new Date(),
                              "0"));
        dummy.add(new ArticleEntity(0,
                              "201406201266632",
                              "[羽っ鳥もさく共和国]EXPLORER",
                              "单曲碟是用v家唱的",// DESCRIPTION
                              "201405021242370",
                              "1",
                              new Date(),
                              "0"));

        return dummy;
    }

    @Override
    public ArticleEntity findOne(final Integer id) {
        try {
            final String sql = "Select " + "id      as id"
                    + "        from M_article "
                    + "     where "
                    + "             id = ?";
            this.log.info("[sql] = " + sql);
            this.log.info("[id] = " + id);
            final Map<String, Object> result = this.getMysqlDao()
                                                   .queryForMap(sql, id);
            final ArticleEntity article = new ArticleEntity();
            article.setId((Integer) result.get("id"));
            return article;
        } catch (final Exception e) {
            return null;
        }
    }

    @Override
    public void insert(final ArticleEntity article) {
        final String sql = "INSERT INTO " + "m_article("
                + "user_id,"
                + "title,"
                + "description,"
                + "publish_flg,"
                + "publish_date,"
                + "recommend_flg"
                + ") VALUES ("
                + "?,?,?,?,?,?"
                + ")";
        this.getMysqlDao().update(sql,
                                  article.getUser().getId(),
                                  article.getTitle(),
                                  article.getDescription(),
                                  article.getPublishFlg(),
                                  article.getPublishDate(),
                                  article.getRecommend());
        article.setId(this.findId());
    }

    @Override
    public void save(final ArticleEntity article) {
        this.insert(article);
    }

    @Override
    public void deleteAll() {
        this.deleteAll("m_article");
    }

    @Override
    public void deleteOne(final Integer id) {
        throw new RuntimeException(new MethodNotSupportedException(""));
    }

    @Override
    public void update(final ArticleEntity bean) {
        throw new RuntimeException(new MethodNotSupportedException(""));
    }
}
