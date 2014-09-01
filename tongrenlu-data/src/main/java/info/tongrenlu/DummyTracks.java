package info.tongrenlu;

import info.tongrenlu.dummy.DummyHelper;
import info.tongrenlu.entity.DtoBean;
import info.tongrenlu.entity.FileEntity;
import info.tongrenlu.entity.MusicEntity;
import info.tongrenlu.entity.TrackEntity;
import info.tongrenlu.entity.UserEntity;
import info.tongrenlu.jdbc.FileManager;
import info.tongrenlu.jdbc.MusicManagerMysql;
import info.tongrenlu.jdbc.ObjectManager;
import info.tongrenlu.jdbc.TrackManager;
import info.tongrenlu.jdbc.TrackRateManager;
import info.tongrenlu.jdbc.UserManager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DummyTracks {

    @Autowired
    private MusicManagerMysql musicManager;
    @Autowired
    private FileManager fileManager;
    @Autowired
    private TrackManager trackManager;
    @Autowired
    private UserManager userManager;
    @Autowired
    private ObjectManager objectManager = null;
    @Autowired
    private TrackRateManager trackRateManager = null;

    public void execute() {
        final List<MusicEntity> musiclist = this.musicManager.findAll();
        for (final MusicEntity musicEntity : musiclist) {
            for (int i = 1; i < 5 + DummyHelper.nextInt(20); i++) {
                final FileEntity file = this.createDummyFile(musicEntity, i);
                final TrackEntity track = this.createDummyTrack(file);
                this.createTrackRate(track);
            }
        }
    }

    private FileEntity createDummyFile(final MusicEntity musicEntity,
                                       final int i) {
        final FileEntity file = new FileEntity();
        file.setArticle(musicEntity);
        file.setName("file" + i);
        file.setContentType("audio");
        file.setExtension("mp3");
        file.setOrderNo(i);

        this.fileManager.insert(file);

        return file;
    }

    private TrackEntity createDummyTrack(final FileEntity file) {
        final TrackEntity track = new TrackEntity();
        track.setId(file.getId());
        track.setFile(file);
        track.setName("track" + file.getOrderNo());
        track.setArtist("artist");
        track.setOriginal("original");
        this.trackManager.insert(track);
        return track;
    }

    private void createTrackRate(final TrackEntity track) {
        for (int i = 1; i < DummyHelper.nextInt(10); i++) {
            final UserEntity user = this.findUser(DummyHelper.randomUserId());
            if (user != null) {
                this.trackRateManager.insert(track,
                                             user,
                                             DummyHelper.nextInt(5) + 1);
            }
        }
    }

    private UserEntity findUser(final String objectId) {
        final DtoBean userObject = this.objectManager.findOne(objectId);
        if (userObject == null) {
            return null;
        }
        return this.userManager.findOne(userObject.getId());
    }
}
