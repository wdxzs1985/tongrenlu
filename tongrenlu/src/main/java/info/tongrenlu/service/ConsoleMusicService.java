package info.tongrenlu.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import info.tongrenlu.constants.CommonConstants;
import info.tongrenlu.domain.ArticleBean;
import info.tongrenlu.domain.FileBean;
import info.tongrenlu.domain.MusicBean;
import info.tongrenlu.domain.TagBean;
import info.tongrenlu.domain.TrackBean;
import info.tongrenlu.manager.ArticleManager;
import info.tongrenlu.manager.FileManager;
import info.tongrenlu.manager.TagManager;
import info.tongrenlu.manager.TrackManager;
import info.tongrenlu.support.PaginateSupport;

@Service
@Transactional
public class ConsoleMusicService {

    public static final Pattern ARTICLE_ID_PATTERN = Pattern.compile("http://www.tongrenlu.info/music/([0-9]{4,6})");

    @Autowired
    private final ArticleManager articleManager = null;
    @Autowired
    private final TagManager tagManager = null;
    @Autowired
    private final FileManager fileManager = null;
    @Autowired
    private final TrackManager trackManager = null;

    @Transactional
    public boolean doCreate(
                            final MusicBean musicBean, final String[] tags,
                            final MultipartFile cover, final MultipartFile xfd,
                            final Map<String, Object> model,
                            final Locale locale) {
        if (this.validateForCreate(musicBean, model, locale)) {
            this.articleManager.insert(musicBean);
            if (ArrayUtils.isNotEmpty(tags)) {
                for (final String tag : tags) {
                    final TagBean tagBean = this.tagManager.create(tag);
                    this.articleManager.addTag(musicBean, tagBean);
                }
            }
            this.fileManager.saveCover(musicBean, cover);
            this.fileManager.saveXFD(musicBean, xfd);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean doEdit(
                          final MusicBean musicBean, final String[] tags,
                          final MultipartFile cover, final MultipartFile xfd,
                          final Map<String, Object> model,
                          final Locale locale) {
        if (this.validateForEdit(musicBean, model, locale)) {
            this.articleManager.update(musicBean);
            this.articleManager.removeTags(musicBean);
            if (ArrayUtils.isNotEmpty(tags)) {
                for (final String tag : tags) {
                    final TagBean tagBean = this.tagManager.create(tag);
                    this.articleManager.addTag(musicBean, tagBean);
                }
            }

            // if (CommonConstants.is(musicBean.getPublishFlg())) {
            // final List<TrackBean> trackList = this.getTrackList(musicBean);
            // this.saveMusicDocument(musicBean, trackList, tags);
            // }
            this.fileManager.saveCover(musicBean, cover);
            this.fileManager.saveXFD(musicBean, xfd);
            return true;
        }
        return false;
    }

    @Transactional
    public void doDelete(
                         final MusicBean musicBean) {
        this.articleManager.delete(musicBean);
        // this.deleteMusicDocument(musicBean);
        this.fileManager.deleteArticle(musicBean);
    }

    public void searchMusic(
                            final PaginateSupport<MusicBean> paginate) {
        final int itemCount = this.articleManager.countMusic(paginate.getParams());
        paginate.setItemCount(itemCount);
        paginate.compute();

        final List<MusicBean> items = this.articleManager.searchMusic(paginate.getParams());
        paginate.setItems(items);
    }

    public MusicBean getById(
                             final Integer id) {
        return this.articleManager.getMusicById(id);
    }

    public String[] getTags(
                            final MusicBean musicBean) {
        final List<TagBean> tagList = this.articleManager.getTags(musicBean);
        final List<String> tags = new ArrayList<String>();
        for (final TagBean tagBean : tagList) {
            tags.add(tagBean.getTag());
        }
        return tags.toArray(new String[] {});
    }

    public List<FileBean> getTrackFileList(
                                           final MusicBean musicBean) {
        final Integer articleId = musicBean.getId();
        return this.articleManager.getFiles(articleId, FileManager.AUDIO);
    }

    public List<FileBean> getBookletFileList(
                                             final MusicBean musicBean) {
        final Integer articleId = musicBean.getId();
        return this.articleManager.getFiles(articleId, FileManager.IMAGE);
    }

    public List<Map<String, Object>> wrapFileBeanList(
                                                      final List<FileBean> fileList) {
        List<Map<String, Object>> files = Collections.emptyList();
        if (CollectionUtils.isNotEmpty(fileList)) {
            files = new ArrayList<Map<String, Object>>();
            for (final FileBean fileBean : fileList) {
                final Map<String, Object> fileModel = new HashMap<String, Object>();
                files.add(this.wrapFileBean(fileBean, fileModel));
            }
        }
        return files;
    }

    public Map<String, Object> wrapFileBean(
                                            final FileBean fileBean,
                                            final Map<String, Object> fileModel) {
        fileModel.put("id", fileBean.getId());
        fileModel.put("name", fileBean.getName());
        fileModel.put("checksum", fileBean.getChecksum());
        fileModel.put("articleId", fileBean.getArticleId());
        return fileModel;
    }

    @Transactional
    public boolean addTrackFile(
                                final FileBean fileBean,
                                final MultipartFile upload,
                                final Map<String, Object> fileModel,
                                final Locale locale) {
        if (this.validateForTrackFile(upload, fileModel, locale)) {

            this.articleManager.addFile(fileBean);

            final TrackBean trackBean = new TrackBean();
            trackBean.setId(fileBean.getId());
            trackBean.setName(fileBean.getName());
            this.trackManager.addTrack(trackBean);
            this.fileManager.saveFile(FileManager.MUSIC, fileBean, upload);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean addBookletFile(
                                  final FileBean fileBean,
                                  final MultipartFile upload,
                                  final Map<String, Object> fileModel,
                                  final Locale locale) {
        if (this.validateForBookletFile(upload, fileModel, locale)) {
            this.articleManager.addFile(fileBean);
            this.fileManager.saveFile(FileManager.MUSIC, fileBean, upload);
            this.fileManager.convertImage(FileManager.MUSIC, fileBean);
            return true;
        }
        return false;
    }

    @Transactional
    public void removeFile(
                           final Integer fileId) {
        final FileBean fileBean = this.articleManager.getFile(fileId);
        if (fileBean != null) {
            this.articleManager.deleteFile(fileBean);

            if (FileManager.AUDIO.equals(fileBean.getContentType())) {
                final TrackBean trackBean = new TrackBean();
                trackBean.setFileBean(fileBean);
                this.trackManager.deleteTrack(trackBean);

                // this.deleteTrackDocument(trackBean);
            }

            this.fileManager.deleteFile(FileManager.MUSIC, fileBean);
        }
    }

    public List<TrackBean> getTrackList(
                                        final MusicBean musicBean) {
        final Integer articleId = musicBean.getId();
        return this.trackManager.getTrackList(articleId);
    }

    @Transactional
    public void updateTrackList(
                                final List<TrackBean> trackList,
                                final MusicBean musicBean) {
        for (final TrackBean trackBean : trackList) {
            this.trackManager.updateTrack(trackBean);
            this.articleManager.updateFile(trackBean.getFileBean());

            // if (CommonConstants.is(musicBean.getPublishFlg())) {
            // final String[] tags = this.getTags(musicBean);
            // this.saveMusicDocument(musicBean, trackList, tags);
            // }
        }
    }

    @Transactional
    public void updateBookletFile(
                                  final List<FileBean> fileList) {
        for (final FileBean fileBean : fileList) {
            this.articleManager.updateFile(fileBean);
        }
    }

    @Transactional
    public void publish(
                        final MusicBean musicBean) {
        this.articleManager.publish(musicBean);
        final List<TrackBean> trackList = this.getTrackList(musicBean);
        // final String[] tags = this.getTags(musicBean);
        // this.saveMusicDocument(musicBean, trackList, tags);
        if (CollectionUtils.isNotEmpty(trackList)) {
            final TrackBean trackBean = trackList.get(0);
            final FileBean fileBean = trackBean.getFileBean();
            this.fileManager.saveXFD(fileBean.getArticleId(),
                                     fileBean.getChecksum());
        }
    }

    public void free(
                     final MusicBean musicBean) {
        this.articleManager.free(musicBean);
        // final String[] tags = this.getTags(musicBean);
        // final List<TrackBean> trackList = this.getTrackList(musicBean);
        // this.saveMusicDocument(musicBean, trackList, tags);
    }

    private boolean validateForCreate(
                                      final MusicBean inputArticle,
                                      final Map<String, Object> model,
                                      final Locale locale) {
        boolean isValid = true;
        if (!this.articleManager.validateTitle(inputArticle.getTitle(),
                                               "titleError",
                                               model,
                                               locale)) {
            isValid = false;
        }
        return isValid;
    }

    private boolean validateForEdit(
                                    final MusicBean inputArticle,
                                    final Map<String, Object> model,
                                    final Locale locale) {
        boolean isValid = true;
        if (!this.articleManager.validateTitle(inputArticle.getTitle(),
                                               "titleError",
                                               model,
                                               locale)) {
            isValid = false;
        }
        return isValid;
    }

    private boolean validateForTrackFile(
                                         final MultipartFile upload,
                                         final Map<String, Object> fileModel,
                                         final Locale locale) {
        boolean isValid = true;
        if (!this.fileManager.validateContentType(upload.getContentType(),
                                                  FileManager.ACCEPT_AUDIO,
                                                  "error",
                                                  fileModel,
                                                  locale)) {
            isValid = false;
        }
        return isValid;
    }

    private boolean validateForBookletFile(
                                           final MultipartFile upload,
                                           final Map<String, Object> fileModel,
                                           final Locale locale) {
        boolean isValid = true;
        if (!this.fileManager.validateContentType(upload.getContentType(),
                                                  FileManager.ACCEPT_IMAGE,
                                                  "error",
                                                  fileModel,
                                                  locale)) {
            isValid = false;
        }
        return isValid;
    }

    // @Transactional
    // public void saveMusicDocument(final MusicBean musicBean, final
    // List<TrackBean> trackList, final String[] tags) {
    // final Integer articleId = musicBean.getId();
    // ArticleDocument articleDocument = this.articleRepository.findOne("m" +
    // articleId);
    // if (articleDocument == null) {
    // articleDocument = new MusicDocument(articleId);
    // }
    // articleDocument.setTitle(musicBean.getTitle());
    // articleDocument.setDescription(musicBean.getDescription());
    //
    // final Set<String> tagSet = new HashSet<String>();
    //
    // if (ArrayUtils.isNotEmpty(tags)) {
    // tagSet.addAll(Arrays.asList(tags));
    // }
    //
    // if (CollectionUtils.isNotEmpty(trackList)) {
    // for (final TrackBean track : trackList) {
    // final Integer fileId = track.getId();
    // ArticleDocument trackDocument = this.articleRepository.findOne("t" +
    // fileId);
    // if (trackDocument == null) {
    // trackDocument = new TrackDocument(fileId);
    // trackDocument.setArticleId(articleId);
    // trackDocument.setTitle(articleDocument.getTitle());
    // }
    //
    // trackDocument.setTrack(track.getName());
    // trackDocument.setInstrumental(CommonConstants.is(track.getInstrumental()));
    // trackDocument.setArtist(StringUtils.split(track.getArtist(), ","));
    // trackDocument.setOriginal(StringUtils.split(track.getOriginal(), "\n"));
    //
    // this.articleRepository.save(trackDocument);
    //
    // tagSet.add(trackDocument.getTrack());
    //
    // if (ArrayUtils.isNotEmpty(trackDocument.getArtist())) {
    // tagSet.addAll(Arrays.asList(trackDocument.getArtist()));
    // }
    // if (ArrayUtils.isNotEmpty(trackDocument.getOriginal())) {
    // tagSet.addAll(Arrays.asList(trackDocument.getOriginal()));
    // }
    // }
    // }
    //
    // articleDocument.setTags(tagSet.toArray(new String[] {}));
    //
    // this.articleRepository.save(articleDocument);
    // }

    // @Transactional
    // public void deleteMusicDocument(final MusicBean musicBean) {
    // final Integer articleId = musicBean.getId();
    // final String id = "m" + articleId;
    // this.articleRepository.delete(id);
    //
    // for (final ArticleDocument trackDocument :
    // this.trackRepository.findByArticleId(articleId)) {
    // this.articleRepository.delete(trackDocument);
    // }
    // }
    //
    // @Transactional
    // public void deleteTrackDocument(final TrackBean trackBean) {
    // final String id = "t" + trackBean.getId();
    // this.articleRepository.delete(id);
    // }

    public int countUnpublish() {
        final Map<String, Object> params = new HashMap<>();
        params.put("publishFlg", new String[] { ArticleBean.UNPUBLISH });
        return this.articleManager.countMusic(params);
    }

    public List<Map<String, Object>> getPlaylist(
                                                 final MusicBean musicBean) {
        final List<Map<String, Object>> playlist = new ArrayList<Map<String, Object>>();
        this.addXFDPlayable(musicBean, playlist);
        this.addTrackPlayable(musicBean, playlist);
        return playlist;
    }

    private void addXFDPlayable(
                                final MusicBean musicBean,
                                final List<Map<String, Object>> playlist) {
        final Map<String, Object> playable = new HashMap<String, Object>();
        playable.put("title", musicBean.getTitle());
        playable.put("xfd", true);
        playlist.add(playable);
    }

    private void addTrackPlayable(
                                  final MusicBean musicBean,
                                  final List<Map<String, Object>> playlist) {
        final List<TrackBean> trackList = this.getTrackList(musicBean);
        for (final TrackBean trackBean : trackList) {
            final Map<String, Object> playable = new HashMap<String, Object>();
            playable.put("id", trackBean.getId());
            playable.put("title", trackBean.getName());
            playable.put("artist", trackBean.getArtist());
            playable.put("checksum", trackBean.getFileBean().getChecksum());
            playable.put("articleId", trackBean.getFileBean().getArticleId());
            playable.put("original",
                         StringUtils.split(trackBean.getOriginal(), '\n'));
            playable.put("instrumental",
                         CommonConstants.is(trackBean.getInstrumental()));
            playable.put("rate", trackBean.getRate());
            playlist.add(playable);
        }
    }

    public MusicBean getByUrl(
                              final String url) {
        final Matcher matcher = ARTICLE_ID_PATTERN.matcher(url);
        if (matcher.find()) {
            final Integer articleId = Integer.valueOf(matcher.group(1));
            return this.getById(articleId);
        }
        return null;
    }

}
