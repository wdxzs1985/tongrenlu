package info.tongrenlu.jdbc;

import info.tongrenlu.entity.MusicEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.MethodNotSupportedException;
import org.springframework.stereotype.Component;

@Component
public class MusicManagerMysql extends ManagerSupport implements
        Manager<MusicEntity, Integer> {

    @Override
    public List<MusicEntity> findAll() {
        final List<MusicEntity> result = new ArrayList<MusicEntity>();
        final String sql = "Select " + "m_article.id      as id "
                           + "    ,   m_article.TITLE         as title"
                           + "    ,   m_article.DESCRIPTION       as description"
                           + "        from "
                           + "      r_music join m_article on r_music.id = m_article.id"
                           + "  where "
                           + "  r_music.DEL_FLG = '0' "
                           + "  and m_article.del_flg = '0' "
                           + "      order by id asc ";
        final List<Map<String, Object>> resultList = this.getMysqlDao()
                                                         .queryForList(sql);
        for (final Map<String, Object> map : resultList) {
            result.add(this.mapToBean(map));
        }
        return result;
    }

    private MusicEntity mapToBean(final Map<String, Object> map) {
        final MusicEntity entity = new MusicEntity();
        entity.setId((Integer) map.get("id"));
        entity.setTitle((String) map.get("title"));
        entity.setDescription((String) map.get("description"));
        return entity;
    }

    @Override
    public MusicEntity findOne(final Integer id) {
        throw new RuntimeException(new MethodNotSupportedException(""));
    }

    @Override
    public void insert(final MusicEntity music) {
        final String sql = "INSERT INTO " + "r_music("
                           + "id"
                           + ") VALUES ("
                           + "?"
                           + ")";
        this.getMysqlDao().update(sql, music.getId());
    }

    @Override
    public void save(final MusicEntity music) {
        this.insert(music);
    }

    @Override
    public void deleteAll() {
        this.deleteAll("r_music");
    }

    @Override
    public void deleteOne(final Integer id) {
        throw new RuntimeException(new MethodNotSupportedException(""));
    }

    @Override
    public void update(final MusicEntity bean) {
        throw new RuntimeException(new MethodNotSupportedException(""));
    }
}
