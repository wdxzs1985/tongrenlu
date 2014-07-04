package info.tongrenlu.service;

import info.tongrenlu.entity.TrackEntity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.MethodNotSupportedException;
import org.springframework.stereotype.Component;

@Component
public class TrackRepository extends RepositorySupport implements Repository<TrackEntity, Integer> {

    @Override
    public List<TrackEntity> findAll() {
        try {
            final String sql = "Select " + "FILE_ID      as fileId"
                    + "    ,   SONG_TITLE                  as name"
                    + "    ,   LEAD_ARTIST                 as artist"
                    + "    ,   ORIGINAL_TITLE                 as original"
                    + "        from M_TRACK "
                    + "         where DEL_FLG = '0'";

            return this.getOracleDao().queryForList(sql, TrackEntity.class);
        } catch (final Exception e) {
            return this.dummy();
        }

    }

    private List<TrackEntity> dummy() {
        final List<TrackEntity> dummy = new ArrayList<TrackEntity>();
        dummy.add(new TrackEntity(0, "201406301269972",// FILE_ID
                                  "Salvation",// SONG_TITLE
                                  "13.c.Gilbert",// LEAD_ARTIST
                                  "東方風神録／ネイティブフェイス"// ORIGINAL_TITLE
        ));
        dummy.add(new TrackEntity(0, "201406301269973",// FILE_ID
                                  "Muppet Puppeteer (Goliath form mix)",// NAME
                                  "13.c.Gilbert",// LEAD_ARTIST
                                  "東方怪綺談／不思議の国のアリス"// ORIGINAL_TITLE
        ));
        dummy.add(new TrackEntity(0, "201406301269974",// FILE_ID
                                  "Volatilia ad Purpurae",// NAME
                                  "13.c.Gilbert",// LEAD_ARTIST
                                  "東方永夜抄／月まで届け、不死の煙"// ORIGINAL_TITLE
        ));
        dummy.add(new TrackEntity(0, "201406301269975",// FILE_ID
                                  "Laevatein -Bloody Crusade-",// NAME
                                  "13.c.Gilbert",// LEAD_ARTIST
                                  "東方紅魔郷／U.N.オーエンは彼女なのか？"// ORIGINAL_TITLE
        ));
        dummy.add(new TrackEntity(0, "201406301269976",// FILE_ID
                                  "Gungnir -Annihilation-",// NAME
                                  "13.c.Gilbert",// LEAD_ARTIST
                                  "東方風神録／ネイティブフェイス"// ORIGINAL_TITLE
        ));
        return dummy;
    }

    @Override
    public TrackEntity findOne(final Integer id) {
        throw new RuntimeException(new MethodNotSupportedException(""));
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
        this.insert(track);
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
