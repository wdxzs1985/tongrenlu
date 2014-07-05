package info.tongrenlu.jdbc;

import info.tongrenlu.entity.ComicEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.MethodNotSupportedException;
import org.springframework.stereotype.Component;

@Component
public class ComicManager extends ManagerSupport implements
        Manager<ComicEntity, Integer> {

    @Override
    public List<ComicEntity> findAll() {
        final List<ComicEntity> result = new ArrayList<ComicEntity>();
        final String sql = "Select " + "ARTICLE_ID      as articleId,"
                           + "      RED_FLG      as redFlg,"
                           + "      TRANSLATE_FLG      as translateFlg"
                           + "        from "
                           + "  R_Comic "
                           + "         where "
                           + "  DEL_FLG = '0'"
                           + "      order by articleId asc";
        final List<Map<String, Object>> resultList = this.getOracleDao()
                                                         .queryForList(sql);
        for (final Map<String, Object> map : resultList) {
            result.add(this.mapToBean(map));
        }
        return result;
    }

    private ComicEntity mapToBean(final Map<String, Object> map) {
        return new ComicEntity((String) map.get("articleId"),
                               (String) map.get("redFlg"),
                               (String) map.get("translateFlg"));
    }

    @Override
    public ComicEntity findOne(final Integer id) {
        throw new RuntimeException(new MethodNotSupportedException(""));
    }

    @Override
    public void insert(final ComicEntity comic) {
        final String sql = "INSERT INTO " + "r_comic("
                           + "id , red_flg, translate_flg"
                           + ") VALUES ("
                           + "?,?,?"
                           + ")";
        this.getMysqlDao().update(sql,
                                  comic.getId(),
                                  comic.getRedFlg(),
                                  comic.getTranslateFlg());
    }

    @Override
    public void save(final ComicEntity comic) {
        this.insert(comic);
    }

    @Override
    public void deleteAll() {
        this.deleteAll("r_comic");
    }

    @Override
    public void deleteOne(final Integer id) {
        throw new RuntimeException(new MethodNotSupportedException(""));
    }

    @Override
    public void update(final ComicEntity bean) {
        throw new RuntimeException(new MethodNotSupportedException(""));
    }
}
