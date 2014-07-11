package info.tongrenlu.jdbc;

import info.tongrenlu.entity.MusicEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.MethodNotSupportedException;
import org.springframework.stereotype.Component;

@Component
public class MusicManager extends ManagerSupport implements Manager<MusicEntity, Integer> {

    @Override
    public List<MusicEntity> findAll() {
        final List<MusicEntity> result = new ArrayList<MusicEntity>();
        final String sql = "Select " + "ARTICLE_ID      as articleId"
                + "        from R_MUSIC "
                + "         where DEL_FLG = '0'"
                + "      order by articleId asc";
        final List<Map<String, Object>> resultList = this.getOracleDao()
                                                         .queryForList(sql);
        for (final Map<String, Object> map : resultList) {
            result.add(this.mapToBean(map));
        }
        return result;
    }

    private MusicEntity mapToBean(final Map<String, Object> map) {
        final MusicEntity bean = new MusicEntity((String) map.get("articleId"));
        bean.setId((Integer) map.get("id"));
        return bean;
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
