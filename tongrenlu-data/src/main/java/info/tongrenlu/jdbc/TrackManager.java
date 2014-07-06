package info.tongrenlu.jdbc;

import info.tongrenlu.entity.TrackEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.MethodNotSupportedException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

@Component
public class TrackManager extends ManagerSupport implements
        Manager<TrackEntity, Integer> {

    @Override
    public List<TrackEntity> findAll() {
        final List<TrackEntity> result = new ArrayList<TrackEntity>();
        final String sql = "Select " + "FILE_ID      as fileId"
                           + "    ,   SONG_TITLE                  as name"
                           + "    ,   LEAD_ARTIST                 as artist"
                           + "    ,   ORIGINAL_TITLE                 as original"
                           + "        from M_TRACK "
                           + "         where DEL_FLG = '0'";
        final List<Map<String, Object>> resultList = this.getOracleDao()
                                                         .queryForList(sql);
        for (final Map<String, Object> map : resultList) {
            result.add(this.mapToBean(map));
        }
        return result;
    }

    public TrackEntity findByFileId(final String articleId) {
        final String sql = "Select " + "FILE_ID      as fileId"
                           + "    ,   SONG_TITLE                  as name"
                           + "    ,   LEAD_ARTIST                 as artist"
                           + "    ,   ORIGINAL_TITLE                 as original"
                           + "        from M_TRACK "
                           + "  where "
                           + "      FILE_ID = ?  "
                           + "  and DEL_FLG = '0'";
        this.log.info("[sql] = " + sql);
        this.log.info("[id] = " + articleId);
        final Map<String, Object> result = this.getOracleDao()
                                               .queryForMap(sql, articleId);
        return this.mapToBean(result);
    }

    private TrackEntity mapToBean(final Map<String, Object> map) {
        return new TrackEntity((String) map.get("fileId"),
                               (String) map.get("name"),
                               (String) map.get("artist"),
                               (String) map.get("original"));
    }

    @Override
    public TrackEntity findOne(final Integer id) {
        final String sql = "Select " + "id      as id"
                           + "        from m_track "
                           + "     where "
                           + "             id = ?";
        this.log.info("[sql] = " + sql);
        this.log.info("[id] = " + id);
        try {
            final Map<String, Object> result = this.getMysqlDao()
                                                   .queryForMap(sql, id);
            final TrackEntity bean = new TrackEntity();
            bean.setId((Integer) result.get("id"));
            return bean;
        } catch (final EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void insert(final TrackEntity track) {
        final String sql = "INSERT INTO " + "m_track("
                           + "id,"
                           + "name,"
                           + "artist,"
                           + "original"
                           + ") VALUES ("
                           + "?,?,?,?"
                           + ")";
        this.getMysqlDao().update(sql,
                                  track.getFile().getId(),
                                  track.getName(),
                                  track.getArtist(),
                                  track.getOriginal());
    }

    @Override
    public void save(final TrackEntity track) {
        if (this.findOne(track.getFile().getId()) == null) {
            this.insert(track);
        }
    }

    @Override
    public void deleteAll() {
        this.deleteAll("m_track");
    }

    @Override
    public void deleteOne(final Integer id) {
        throw new RuntimeException(new MethodNotSupportedException(""));
    }

    @Override
    public void update(final TrackEntity bean) {
        throw new RuntimeException(new MethodNotSupportedException(""));
    }
}
