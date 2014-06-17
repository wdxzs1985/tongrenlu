package info.tongrenlu.service.validator;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class ArticleBeanValidator {

    @Autowired
    private MessageSource messageSource = null;

    public boolean validateTitle(final String title,
                                 final Map<String, Object> model) {
        boolean isValid = true;
        if (StringUtils.isBlank(title)) {
            model.put("title_error",
                      this.messageSource.getMessage("ArticleBean.title[Blank]",
                                                    null,
                                                    null));
            isValid = false;
        } else if (StringUtils.length(title) > 100) {
            model.put("title_error",
                      this.messageSource.getMessage("ArticleBean.title[TooLong]",
                                                    new Integer[] { 100 },
                                                    null));
            isValid = false;
        }

        return isValid;
    }

    public boolean validateDescription(final String description,
                                       final Map<String, Object> model) {
        boolean isValid = true;
        if (StringUtils.length(description) > 2000) {
            model.put("description_error",
                      this.messageSource.getMessage("ArticleBean.description[TooLong]",
                                                    new Integer[] { 2000 },
                                                    null));
            isValid = false;
        }
        return isValid;
    }

}
