package info.tongrenlu.service;

import info.tongrenlu.entity.MusicEntity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.MethodNotSupportedException;
import org.springframework.stereotype.Component;

@Component
public class MusicRepository extends RepositorySupport implements Repository<MusicEntity, Integer> {

    @Override
    public List<MusicEntity> findAll() {
        try {
            final String sql = "Select " + "ARTICLE_ID      as articleId"
                    + "        from R_MUSIC "
                    + "         where DEL_FLG = '0'";
            return this.getOracleDao().queryForList(sql, MusicEntity.class);
        } catch (final Exception e) {
            return this.dummy();
        }

    }

    private List<MusicEntity> dummy() {
        final List<MusicEntity> dummy = new ArrayList<MusicEntity>();
        dummy.add(new MusicEntity(0, "201407011270174"));
        dummy.add(new MusicEntity(0, "201406301269970"));
        dummy.add(new MusicEntity(0, "201406291269769"));
        dummy.add(new MusicEntity(0, "201406281269170"));
        dummy.add(new MusicEntity(0, "201406281268773"));
        dummy.add(new MusicEntity(0, "201406281268770"));
        dummy.add(new MusicEntity(0, "201406251268174"));
        dummy.add(new MusicEntity(0, "201406241267979"));
        dummy.add(new MusicEntity(0, "201406201266632"));

        return dummy;
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
