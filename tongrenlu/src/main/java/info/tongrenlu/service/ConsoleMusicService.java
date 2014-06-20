package info.tongrenlu.service;

import info.tongrenlu.domain.MusicBean;
import info.tongrenlu.domain.UserBean;
import info.tongrenlu.manager.MusicDao;
import info.tongrenlu.manager.TagDao;
import info.tongrenlu.support.PaginateSupport;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class ConsoleMusicService {

    @Autowired
    private MusicDao musicDao = null;
    @Autowired
    protected FileService fileDao = null;
    @Autowired
    protected TagDao tagDao = null;
    @Autowired
    private SolrService solrService = null;

    public String doGetIndex(final UserBean loginUser,
                             final Integer page,
                             final String searchQuery,
                             final Model model) {
        final PaginateSupport paginate = new PaginateSupport();
        paginate.setPage(page);
        paginate.setSize(10);
        model.addAttribute("searchQuery", searchQuery);
        model.addAttribute(this.musicDao.getConsoleMusicList(loginUser,
                                                             searchQuery,
                                                             paginate));
        return "console/music/index";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/console/music/input")
    public String doPostInput(final UserBean loginUser,
                              final MusicBean musicBean,
                              final MultipartFile cover,
                              final String[] tagIdArray,
                              final String[] tagArray,
                              final Map<String, Object> model) {
        musicBean.setUserBean(loginUser);
        if (this.musicDao.validateCreateMusic(musicBean, model)) {
            this.musicDao.createMusic(musicBean);
            this.tagDao.addArticleTag(musicBean, tagIdArray);
            this.fileDao.saveCoverFile(musicBean, cover);
            return "redirect:/console/music/finish";
        }
        model.put("articleBean", musicBean);
        model.put("inputTagBeanList",
                  this.tagDao.resolveInputTagBeanList(tagIdArray, tagArray));
        return "console/music/input";
    }

    public String doGetEdit(final UserBean loginUser,
                            final String articleId,
                            final Model model) {
        final MusicBean musicBean = this.musicDao.getMusicById(articleId, null);
        if (musicBean == null) {
            return "console/error/404";
        }
        if (!musicBean.getUserBean().equals(loginUser)) {
            return "console/error/403";
        }
        model.addAttribute("articleBean", musicBean);
        model.addAttribute(this.tagDao.getArticleTag(articleId));
        return "console/music/edit";

    }

    public String doPostEdit(final UserBean loginUser,
                             final String articleId,
                             final MusicBean musicBean,
                             final MultipartFile cover,
                             final String[] tagIdArray,
                             final String[] tagArray,
                             final Map<String, Object> model) {
        final MusicBean music = this.musicDao.getMusicById(articleId, null);
        if (music == null) {
            return "console/error/404";
        }
        if (!musicBean.getUserBean().equals(loginUser)) {
            return "console/error/403";
        }
        if (this.musicDao.validateEditMusic(musicBean, cover, model)) {
            this.musicDao.editMusic(musicBean);
            this.tagDao.addArticleTag(musicBean, tagIdArray);
            this.fileDao.saveCoverFile(musicBean, cover);
            this.solrService.createMusicIndex(musicBean, true);
            return "redirect:/console/music";
        }
        model.put("articleBean", musicBean);
        model.put("inputTagBeanList",
                  this.tagDao.resolveInputTagBeanList(tagIdArray, tagArray));
        return "console/music/edit";
    }

    public String doGetDelete(final UserBean loginUser, final String articleId) {
        final MusicBean musicBean = this.musicDao.getMusicById(articleId, null);
        if (musicBean == null) {
            return "console/error/404";
        }
        if (!musicBean.getUserBean().equals(loginUser)) {
            return "console/error/403";
        }
        this.musicDao.deleteMusic(musicBean);
        return "redirect:/console/music";
    }

    public String doGetCollect(final UserBean loginUser,
                               final Integer page,
                               final Model model) {
        final PaginateSupport paginate = new PaginateSupport();
        paginate.setPage(page);
        paginate.setSize(10);
        model.addAttribute(this.musicDao.getMusicCollectList(loginUser,
                                                             paginate));
        return "console/music/collect";
    }
}
