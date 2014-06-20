package info.tongrenlu.service;

import info.tongrenlu.domain.MusicBean;
import info.tongrenlu.domain.TagBean;
import info.tongrenlu.domain.UserBean;
import info.tongrenlu.manager.AritcleManager;
import info.tongrenlu.manager.MusicDao;
import info.tongrenlu.manager.TagDao;
import info.tongrenlu.manager.TagManager;
import info.tongrenlu.support.PaginateSupport;

import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class ConsoleMusicService {

    @Autowired
    private AritcleManager aritcleManager = null;
    @Autowired
    private TagManager tagManager = null;

    @Transactional
    public MusicBean doCreate(final MusicBean inputMusic,
                              final MultipartFile cover,
                              final String[] tags,
                              final Map<String, Object> model,
                              final Locale locale) {
        if (this.validateForCreate(inputMusic, model, locale)) {
            this.aritcleManager.insertMusic(inputMusic);
            for (final String tag : tags) {
                TagBean tagBean = this.tagManager.getByTag(tag);
                if (tagBean == null) {
                    tagBean = new TagBean();
                    tagBean.setTag(tag);
                    this.tagManager.insert(tagBean);
                }
                this.aritcleManager.addArticleTag(inputMusic, tagBean);
            }
            return inputMusic;
        }
        return null;
    }

    private boolean validateForCreate(final MusicBean inputArticle,
                                      final Map<String, Object> model,
                                      final Locale locale) {
        boolean isValid = true;
        if (!this.aritcleManager.validateTitle(inputArticle.getTitle(),
                                               "titleError",
                                               model,
                                               locale)) {
            isValid = false;
        }
        return isValid;
    }

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
