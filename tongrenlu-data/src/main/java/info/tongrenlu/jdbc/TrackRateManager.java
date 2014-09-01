package info.tongrenlu.jdbc;

import info.tongrenlu.entity.TrackEntity;
import info.tongrenlu.entity.UserEntity;

import org.springframework.stereotype.Component;

@Component
public class TrackRateManager extends ManagerSupport {

    public void insert(final TrackEntity track,
                       final UserEntity user,
                       final int rate) {
        final String sql = "INSERT INTO " + "r_track_rate ("
                + "user_id,"
                + "track_id,"
                + "rate"
                + ") VALUES ("
                + "?,?,?"
                + ")";
        this.getMysqlDao().update(sql, user.getId(), track.getId(), rate);
    }
}
