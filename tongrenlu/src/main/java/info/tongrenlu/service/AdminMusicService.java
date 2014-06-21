package info.tongrenlu.service;

import info.tongrenlu.domain.FileBean;
import info.tongrenlu.domain.MusicBean;
import info.tongrenlu.manager.AritcleManager;
import info.tongrenlu.manager.ArticleDao;
import info.tongrenlu.manager.TagDao;
import info.tongrenlu.support.PaginateSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class AdminMusicService {

    @Autowired
    private AritcleManager aritcleManager = null;

    public PaginateSupport<MusicBean> searchMusic(final String query,
                                                  final Integer pageNumber) {
        final PaginateSupport<MusicBean> paginate = new PaginateSupport<>(pageNumber);
        paginate.addParam("query", query);
        paginate.setPageNumber(pageNumber);
        final int itemCount = this.aritcleManager.countMusic(paginate.getParams());
        paginate.setItemCount(itemCount);
        paginate.compute();

        final List<MusicBean> items = this.aritcleManager.searchMusic(paginate.getParams());
        paginate.setItems(items);

        return paginate;
    }

    @Autowired
    private ArticleDao articleDao = null;
    @Autowired
    private FileService fileDao = null;
    @Autowired
    private TagDao tagDao = null;
    @Autowired
    private SolrService solrService = null;

    public String doGetMusicView(final String articleId, final Model model) {
        // final MusicBean musicBean = this.musicDao.getMusicById(articleId,
        // null);
        // if (musicBean == null) {
        // return "cosole/error/404";
        // }
        // model.addAttribute("articleBean", musicBean);
        model.addAttribute(this.fileDao.getMusicTracks(articleId));
        model.addAttribute(this.tagDao.getArticleTag(articleId));
        return "admin/music/view";
    }

    public String doPostMusicPublish(final String articleId, final Model model) {
        // final MusicBean articleBean = this.musicDao.getMusicById(articleId,
        // null);
        // if (articleBean == null) {
        // return "cosole/error/404";
        // }
        // this.articleDao.publish(articleBean);
        // this.solrService.createMusicIndex(articleBean, true);
        return "redirect:/admin/music";
    }

    public String doGetUploadTrack(final String articleId, final Model model) {
        // final MusicBean music = this.musicDao.getMusicById(articleId, null);
        // if (music == null) {
        // return "cosole/error/404";
        // }
        // model.addAttribute("articleBean", music);
        return "admin/music/track/upload";
    }

    public Map<String, Object> doGetMusicTrackFile(final String articleId,
                                                   final HttpServletRequest request) {
        final Map<String, Object> modelMap = new HashMap<String, Object>();
        final List<Object> fileList = new ArrayList<Object>();
        final List<FileBean> files = this.fileDao.getArticleFiles(articleId,
                                                                  FileService.MP3);
        for (final FileBean fileBean : files) {
            fileList.add(this.fileDao.prepareMp3FileBeanModel(fileBean, request));
        }
        modelMap.put("files", fileList);
        return modelMap;
    }

    public Map<String, Object> doPostMusicTrackFile(final String articleId,
                                                    final MultipartFile[] files,
                                                    final HttpServletRequest request) {
        final Map<String, Object> modelMap = new HashMap<String, Object>();
        final List<Object> fileList = new ArrayList<Object>();
        if (ArrayUtils.isNotEmpty(files)) {
            for (final MultipartFile fileItem : files) {
                final FileBean fileBean = this.fileDao.createMusicTrackInfo(articleId,
                                                                            fileItem);
                fileList.add(this.fileDao.prepareMp3FileBeanModel(fileBean,
                                                                  request));
            }
        }
        modelMap.put("files", fileList);
        return modelMap;
    }

    public String doGetTrackSort(final String articleId, final Model model) {
        // final MusicBean music = this.musicDao.getMusicById(articleId, null);
        // if (music == null) {
        // return "cosole/error/404";
        // }
        // model.addAttribute("articleBean", music);
        // final List<TrackBean> trackList =
        // this.fileDao.getMusicTracks(articleId);
        // model.addAttribute(trackList);
        return "admin/music/track/sort";
    }

    public String doPostTrackSort(final String articleId,
                                  final String[] fileIdArray,
                                  final String[] songTitleArray,
                                  final String[] leadArtistArray,
                                  final String[] originalTitleArray) {
        // final MusicBean music = this.musicDao.getMusicById(articleId, null);
        // if (music == null) {
        // return "cosole/error/404";
        // }
        // if (ArrayUtils.isNotEmpty(fileIdArray)) {
        // this.fileDao.updateTracks(fileIdArray,
        // songTitleArray,
        // leadArtistArray,
        // originalTitleArray,
        // music);
        // }
        return "redirect:/admin/music/" + articleId;
    }

    public String doGetBookletUpload(final String articleId, final Model model) {
        // final MusicBean music = this.musicDao.getMusicById(articleId, null);
        // if (music == null) {
        // return "cosole/error/404";
        // }
        // model.addAttribute("articleBean", music);
        return "admin/music/booklet/upload";
    }

    public Map<String, Object> doGetBookletFile(final String articleId,
                                                final HttpServletRequest request) {
        final Map<String, Object> modelMap = new HashMap<String, Object>();
        final List<Object> fileList = new ArrayList<Object>();
        final List<FileBean> files = this.fileDao.getArticleFiles(articleId,
                                                                  FileService.JPG);
        for (final FileBean fileBean : files) {
            fileList.add(this.fileDao.prepareJpgFileBeanModel(fileBean, request));
        }
        modelMap.put("files", fileList);
        return modelMap;
    }

    public Map<String, Object> doPostBookletFile(final String articleId,
                                                 final MultipartFile[] files,
                                                 final HttpServletRequest request) {
        final Map<String, Object> modelMap = new HashMap<String, Object>();
        final List<Object> fileList = new ArrayList<Object>();
        if (ArrayUtils.isNotEmpty(files)) {
            for (final MultipartFile fileItem : files) {
                final FileBean fileBean = this.fileDao.createImageFileInfo(articleId,
                                                                           fileItem);
                fileList.add(this.fileDao.prepareJpgFileBeanModel(fileBean,
                                                                  request));
            }
        }
        modelMap.put("files", fileList);
        return modelMap;
    }

    public String doGetBookletSort(final String articleId, final Model model) {
        // final MusicBean music = this.musicDao.getMusicById(articleId, null);
        // if (music == null) {
        // return "cosole/error/404";
        // }
        // model.addAttribute("articleBean", music);
        // final List<FileBean> fileList =
        // this.fileDao.getArticleFiles(articleId,
        // FileManager.JPG);
        // model.addAttribute(fileList);
        return "admin/music/booklet/sort";
    }

    public String doPostBookletSort(final String articleId,
                                    final String[] fileIdArray) {
        // final MusicBean music = this.musicDao.getMusicById(articleId, null);
        // if (music == null) {
        // return "cosole/error/404";
        // }
        // if (ArrayUtils.isNotEmpty(fileIdArray)) {
        // this.fileDao.sortFiles(fileIdArray);
        // }
        return "redirect:/admin/music/" + articleId;
    }
}
