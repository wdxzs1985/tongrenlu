package info.tongrenlu.jdbc;

import info.tongrenlu.entity.ComicEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.MethodNotSupportedException;
import org.springframework.stereotype.Component;

@Component
public class ComicManagerMysql extends ManagerSupport implements
        Manager<ComicEntity, Integer> {

    @Override
    public List<ComicEntity> findAll() {
        final List<ComicEntity> result = new ArrayList<ComicEntity>();
        final String sql = "Select " + "m_article.id      as id "
                           + "    ,   m_article.TITLE         as title"
                           + "    ,   m_article.DESCRIPTION       as description"
                           + "        from "
                           + "      r_comic join m_article on r_comic.id = m_article.id"
                           + "  where "
                           + "  r_comic.DEL_FLG = '0' "
                           + "  and m_article.del_flg = '0' "
                           + "      order by id asc ";
        final List<Map<String, Object>> resultList = this.getMysqlDao()
                                                         .queryForList(sql);
        for (final Map<String, Object> map : resultList) {
            result.add(this.mapToBean(map));
        }
        return result;
    }

    private ComicEntity mapToBean(final Map<String, Object> map) {
        final ComicEntity entity = new ComicEntity();
        entity.setId((Integer) map.get("id"));
        entity.setTitle((String) map.get("title"));
        entity.setDescription((String) map.get("description"));
        return entity;
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
