package info.tongrenlu.dummy;

import info.tongrenlu.entity.TrackEntity;
import info.tongrenlu.jdbc.TrackManager;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class TrackManagerDummy extends TrackManager {

    @Override
    public List<TrackEntity> findByArticleId(final Integer articleId) {
        final List<TrackEntity> result = new ArrayList<TrackEntity>();
        for (int i = 1; i < DummyHelper.nextInt(10); i++) {
            final TrackEntity track = new TrackEntity();
            track.setName("track" + i);
            track.setArtist("artist");
            result.add(track);
        }
        return result;
    }
}
