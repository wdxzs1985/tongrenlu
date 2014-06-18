package info.tongrenlu.service;

import info.tongrenlu.constants.ConstantsFactoryBean;
import info.tongrenlu.domain.ComicBean;
import info.tongrenlu.domain.FileBean;
import info.tongrenlu.domain.MusicBean;
import info.tongrenlu.domain.UserBean;
import info.tongrenlu.service.dao.ArticleDao;
import info.tongrenlu.service.dao.ComicDao;
import info.tongrenlu.service.dao.FileDao;
import info.tongrenlu.service.dao.MusicDao;
import info.tongrenlu.service.dao.TagDao;
import info.tongrenlu.service.dao.UserDao;
import info.tongrenlu.support.PaginateSupport;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

@Service
@Transactional
public class AdminToolsService {

    @Autowired
    protected ConstantsFactoryBean constantsBean = null;
    @Autowired
    private ArticleDao articleDao = null;
    @Autowired
    private UserDao userDao = null;
    @Autowired
    private FileDao fileDao = null;
    @Autowired
    private ComicDao comicDao = null;
    @Autowired
    private MusicDao musicDao = null;
    @Autowired
    private TagDao tagDao = null;
    @Autowired
    private SolrService solrsService = null;

    public String doGetToolsIndex(final Model model) {
        // final File disk = new File(this.constantsBean.getInputPath());
        //
        // final long diskSpace = disk.getTotalSpace();
        // final long freeSpace = disk.getFreeSpace();
        // final long usedSpace = FileUtils.sizeOfDirectory(disk);
        // final long otherSpace = diskSpace - freeSpace - usedSpace;
        //
        // model.addAttribute("diskSpace",
        // FileUtils.byteCountToDisplaySize(diskSpace));
        // model.addAttribute("usedSpace",
        // FileUtils.byteCountToDisplaySize(usedSpace));
        // model.addAttribute("otherSpace",
        // FileUtils.byteCountToDisplaySize(otherSpace));
        // model.addAttribute("freeSpace",
        // FileUtils.byteCountToDisplaySize(freeSpace));
        // model.addAttribute("usedSpacePercent",
        // Math.floor(usedSpace * 100 / diskSpace));
        // model.addAttribute("otherSpacePercent",
        // Math.floor(otherSpace * 100 / diskSpace));
        return "admin/tools";
    }

    public void doGetConvertCover(final String articleId) {
        // final List<ArticleBean> articleList =
        // this.articleDao.getArticleList(articleId);
        // for (final ArticleBean articleBean : articleList) {
        // this.fileDao.convertCover(articleBean.getArticleId(), "cover");
        // }
    }

    public void doGetConvertAvatar(final String userId) {
        final List<UserBean> userList = this.userDao.getUserList(userId);
        for (final UserBean userBean : userList) {
            this.fileDao.convertCover(userBean.getUserId(), "avatar");
        }
    }

    public void doPostConvertThumbnail(final String articleId) {
        // final List<ArticleBean> articleList =
        // this.articleDao.getArticleList(articleId);
        // for (final ArticleBean articleBean : articleList) {
        // this.convertThumbnail(articleBean.getArticleId());
        // }
    }

    protected void convertThumbnail(final String articleId) {
        final List<FileBean> files = this.fileDao.getArticleFiles(articleId,
                                                                  FileDao.JPG);
        for (final FileBean fileBean : files) {
            final File inputFile = this.fileDao.getJpgFile(articleId,
                                                           fileBean.getFileId());
            if (inputFile.isFile()) {
                this.fileDao.convertThumbnail(fileBean, inputFile);
            }
        }
    }

    public void doPostCreateSolrIndex() throws SolrServerException, IOException {
        final PaginateSupport paginate = new PaginateSupport();
        paginate.setPage(1);
        paginate.setSize(Integer.MAX_VALUE);
        final List<?> comicList = this.comicDao.getAdminComicList(paginate, "")
                                               .getItems();
        for (final Object next : comicList) {
            final ComicBean comicBean = (ComicBean) next;
            this.solrsService.createComicIndex(comicBean, false);
        }

        final PaginateSupport musicPaginate = new PaginateSupport();
        musicPaginate.setPage(1);
        musicPaginate.setSize(Integer.MAX_VALUE);
        final List<?> musicList = this.musicDao.getAdminMusicList(musicPaginate,
                                                                  "")
                                               .getItems();
        for (final Object next : musicList) {
            final MusicBean musicBean = (MusicBean) next;
            this.solrsService.createMusicIndex(musicBean, false);
        }
        this.solrsService.commit();
    }

}
