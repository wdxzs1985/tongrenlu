package info.tongrenlu.www;

import info.tongrenlu.constants.CommonConstants;
import info.tongrenlu.domain.ComicBean;
import info.tongrenlu.domain.FileBean;
import info.tongrenlu.domain.TagBean;
import info.tongrenlu.domain.UserBean;
import info.tongrenlu.exception.ForbiddenException;
import info.tongrenlu.exception.PageNotFoundException;
import info.tongrenlu.service.HomeComicService;
import info.tongrenlu.support.PaginateSupport;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
@RequestMapping(value = "/comic")
public class HomeComicController {

    @Autowired
    private HomeComicService comicService = null;

    protected void throwExceptionWhenNotAllow(final ComicBean comicBean) {
        if (comicBean == null) {
            throw new PageNotFoundException();
        } else if (!CommonConstants.is(comicBean.getPublishFlg())) {
            throw new ForbiddenException();
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "")
    public String doGetIndex(@RequestParam(value = "p", defaultValue = "1") final Integer pageNumber,
                             @RequestParam(value = "q", required = false) final String query,
                             final Model model) {

        final PaginateSupport<ComicBean> page = new PaginateSupport<>(pageNumber);
        page.addParam("query", query);
        page.addParam("publishFlg", CommonConstants.CHR_TRUE);
        this.comicService.searchComic(page);
        model.addAttribute("page", page);

        return "home/comic/index";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{articleId}")
    public String doGetView(@PathVariable final Integer articleId,
                            @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                            final Model model) {
        final ComicBean comicBean = this.comicService.getById(articleId);

        this.throwExceptionWhenNotAllow(comicBean);

        model.addAttribute("articleBean", comicBean);

        this.comicService.addAccess(comicBean, loginUser);

        return "home/comic/view";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{articleId}/picture")
    @ResponseBody
    public Map<String, Object> doGetPicture(@PathVariable final Integer articleId) {
        final Map<String, Object> model = new HashMap<>();
        final List<FileBean> fileList = this.comicService.getPictureList(articleId);
        model.put("fileList", fileList);
        return model;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{articleId}/like")
    @ResponseBody
    public Map<String, Object> doGetLike(@PathVariable final Integer articleId,
                                         @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                                         final Locale locale) {
        final Map<String, Object> model = new HashMap<>();
        this.comicService.isLike(articleId, loginUser, model, locale);
        return model;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{articleId}/like")
    @ResponseBody
    public Map<String, Object> doPostLike(@PathVariable final Integer articleId,
                                          @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                                          final Locale locale) {
        final Map<String, Object> model = new HashMap<>();
        this.comicService.doLike(articleId, loginUser, model, locale);
        return model;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{articleId}/tag")
    @ResponseBody
    public Map<String, Object> doGetTag(@PathVariable final Integer articleId) {
        final Map<String, Object> model = new HashMap<>();
        final List<TagBean> tagList = this.comicService.getTagList(articleId);
        model.put("tagList", tagList);
        return model;
    }
}
