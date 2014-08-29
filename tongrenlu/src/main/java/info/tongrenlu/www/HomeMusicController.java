package info.tongrenlu.www;

import info.tongrenlu.constants.CommonConstants;
import info.tongrenlu.domain.CommentBean;
import info.tongrenlu.domain.DtoBean;
import info.tongrenlu.domain.FileBean;
import info.tongrenlu.domain.MusicBean;
import info.tongrenlu.domain.TagBean;
import info.tongrenlu.domain.TrackBean;
import info.tongrenlu.domain.UserBean;
import info.tongrenlu.exception.ForbiddenException;
import info.tongrenlu.exception.PageNotFoundException;
import info.tongrenlu.service.CommentService;
import info.tongrenlu.service.HomeMusicService;
import info.tongrenlu.service.TagService;
import info.tongrenlu.support.PaginateSupport;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("LOGIN_USER")
@RequestMapping(value = "/music")
@Transactional
public class HomeMusicController {

    @Autowired
    private MessageSource messageSource = null;
    @Autowired
    private HomeMusicService musicService = null;
    @Autowired
    private TagService tagService = null;
    @Autowired
    private CommentService commentService = null;

    protected void throwExceptionWhenNotAllow(final MusicBean musicBean,
                                              final Locale locale) {
        if (musicBean == null) {
            throw new PageNotFoundException(this.messageSource.getMessage("error.pageNotFound",
                                                                          null,
                                                                          locale));
        } else if (!CommonConstants.is(musicBean.getPublishFlg())) {
            throw new ForbiddenException(this.messageSource.getMessage("error.forbidden",
                                                                       null,
                                                                       locale));
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "")
    public String doGetIndex(@RequestParam(value = "p", defaultValue = "1") final Integer pageNumber,
                             @RequestParam(value = "q", required = false) final String query,
                             final Model model) {

        final PaginateSupport<MusicBean> page = new PaginateSupport<>(pageNumber);
        page.addParam("query", query);
        page.addParam("publishFlg", CommonConstants.CHR_TRUE);
        this.musicService.searchMusic(page);
        model.addAttribute("page", page);

        final List<MusicBean> ranking = this.musicService.getRanking(20);
        model.addAttribute("ranking", ranking);
        return "home/music/index";
    }

    public String doGetView(@PathVariable final Integer articleId,
                            @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                            final Model model,
                            final Locale locale) {
        final MusicBean musicBean = this.musicService.getById(articleId);
        this.throwExceptionWhenNotAllow(musicBean, locale);
        this.musicService.addAccess(musicBean, loginUser);
        model.addAttribute("articleBean", musicBean);
        return "home/music/view";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{articleId}")
    public String doGetViewByArticleId(@PathVariable final String articleId,
                                       @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                                       final Model model,
                                       final Locale locale) {
        if (StringUtils.length(articleId) == 15) {
            final DtoBean bean = this.musicService.getByOldArticleId(articleId);
            if (bean == null) {
                throw new PageNotFoundException(this.messageSource.getMessage("error.pageNotFound",
                                                                              null,
                                                                              locale));
            }
            return "redirect:/music/" + bean.getId();
        } else {
            try {
                final Integer id = Integer.valueOf(articleId);
                return this.doGetView(id, loginUser, model, locale);
            } catch (final Exception e) {
                throw new PageNotFoundException(this.messageSource.getMessage("error.pageNotFound",
                                                                              null,
                                                                              locale));
            }

        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{articleId}/track")
    @ResponseBody
    public Map<String, Object> doGetTrack(@PathVariable final Integer articleId,
                                          @ModelAttribute("LOGIN_USER") final UserBean loginUser) {
        final Map<String, Object> model = new HashMap<>();
        final List<TrackBean> trackList = this.musicService.getTrackList(articleId,
                                                                         loginUser);
        model.put("trackList", trackList);
        return model;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{articleId}/booklet")
    @ResponseBody
    public Map<String, Object> doGetBooklet(@PathVariable final Integer articleId) {
        final Map<String, Object> model = new HashMap<>();
        final List<FileBean> fileList = this.musicService.getBookletList(articleId);
        model.put("fileList", fileList);
        return model;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{articleId}/like")
    @ResponseBody
    public Map<String, Object> doGetLike(@PathVariable final Integer articleId,
                                         @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                                         final Locale locale) {
        final Map<String, Object> model = new HashMap<>();
        final int result = this.musicService.isLike(articleId,
                                                    loginUser,
                                                    model,
                                                    locale);
        model.put("result", result);
        return model;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{articleId}/like")
    @ResponseBody
    public Map<String, Object> doPostLike(@PathVariable final Integer articleId,
                                          @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                                          final Locale locale) {
        final Map<String, Object> model = new HashMap<>();
        final int result = this.musicService.doLike(articleId,
                                                    loginUser,
                                                    model,
                                                    locale);
        model.put("result", result);
        return model;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{articleId}/tag")
    @ResponseBody
    public Map<String, Object> doGetTag(@PathVariable final Integer articleId,
                                        @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                                        final Locale locale) {
        final Map<String, Object> model = new HashMap<>();
        final MusicBean musicBean = this.musicService.getById(articleId);
        this.throwExceptionWhenNotAllow(musicBean, locale);
        final List<TagBean> tagList = this.tagService.getTagByArticle(musicBean);
        model.put("tagList", tagList);
        return model;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{articleId}/tag")
    @ResponseBody
    public Map<String, Object> doPostInputTag(@PathVariable final Integer articleId,
                                              @RequestParam(value = "tags[]", required = false) final String[] tags,
                                              @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                                              final Locale locale) {
        final Map<String, Object> model = new HashMap<>();
        final MusicBean musicBean = this.musicService.getById(articleId);
        this.throwExceptionWhenNotAllow(musicBean, locale);
        this.tagService.addTag(musicBean, tags, loginUser, model, locale);
        return model;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{articleId}/comment")
    @ResponseBody
    public Map<String, Object> doGetComment(@PathVariable final Integer articleId,
                                            @RequestParam(value = "p", defaultValue = "1") final Integer pageNumber,
                                            final Locale locale) {
        final Map<String, Object> model = new HashMap<>();
        final PaginateSupport<CommentBean> page = new PaginateSupport<>(pageNumber);
        page.addParam("articleId", articleId);
        this.commentService.searchMusicComment(page);
        model.put("page", page);
        return model;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{articleId}/comment")
    @ResponseBody
    public Map<String, Object> doPostComment(@PathVariable final Integer articleId,
                                             final String content,
                                             @RequestParam(required = false) final Integer parentId,
                                             @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                                             final Locale locale) {
        final Map<String, Object> model = new HashMap<>();
        final MusicBean musicBean = this.musicService.getById(articleId);

        this.throwExceptionWhenNotAllow(musicBean, locale);

        final CommentBean commentBean = new CommentBean();
        commentBean.setArticleBean(musicBean);
        commentBean.setUserBean(loginUser);
        commentBean.setContent(content);

        final boolean result = this.commentService.doComment(commentBean,
                                                             parentId,
                                                             model,
                                                             locale);
        model.put("result", result);
        return model;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/rate")
    @ResponseBody
    public Map<String, Object> doPostRate(@ModelAttribute("LOGIN_USER") final UserBean loginUser,
                                          final Integer trackId,
                                          final Integer rate,
                                          final Locale locale) {
        final Map<String, Object> model = new HashMap<>();

        final boolean result = this.musicService.saveRate(trackId,
                                                          rate,
                                                          loginUser,
                                                          model,
                                                          locale);
        model.put("result", result);
        return model;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/lucky")
    public String doGetLucky() {
        final MusicBean musicBean = this.musicService.getRandomMusic();
        return "redirect:/music/" + musicBean.getId();
    }

}
