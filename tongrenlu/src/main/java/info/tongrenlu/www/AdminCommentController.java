package info.tongrenlu.www;

import info.tongrenlu.domain.CommentBean;
import info.tongrenlu.exception.PageNotFoundException;
import info.tongrenlu.service.CommentService;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/admin/comment")
@Transactional
public class AdminCommentController {

    @Autowired
    private MessageSource messageSource = null;
    @Autowired
    private final CommentService commentService = null;

    private void throwExceptionWhenNotFound(final CommentBean commentBean, final Locale locale) {
        if (commentBean == null) {
            throw new PageNotFoundException(this.messageSource.getMessage("error.pageNotFound", null, locale));
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{commentId}/delete")
    public String doGetDelete(@PathVariable final Integer commentId, final Model model, final Locale locale) {
        final CommentBean commentBean = this.commentService.getById(commentId);
        this.throwExceptionWhenNotFound(commentBean, locale);
        this.commentService.delete(commentBean);
        return "redirect:/admin/music/" + commentBean.getArticleBean().getId() + "/comment";
    }

}
