package info.tongrenlu.manager;

import info.tongrenlu.domain.ArticleBean;
import info.tongrenlu.domain.ArticleTagBean;
import info.tongrenlu.domain.MusicBean;
import info.tongrenlu.domain.TagBean;
import info.tongrenlu.mapper.ArticleMapper;
import info.tongrenlu.mapper.ArticleTagMapper;
import info.tongrenlu.mapper.MusicMapper;
import info.tongrenlu.mapper.TagMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class AritcleManager {

    public static final int TITLE_LENGTH = 100;

    @Autowired
    private MessageSource messageSource = null;

    @Autowired
    private ArticleMapper articleMapper = null;
    @Autowired
    private ArticleTagMapper articleTagMapper = null;
    @Autowired
    private MusicMapper musicMapper = null;
    @Autowired
    private TagMapper tagMapper = null;

    public void insert(final ArticleBean articleBean) {
        this.articleMapper.insert(articleBean);
        if (articleBean instanceof MusicBean) {
            this.musicMapper.insert((MusicBean) articleBean);
        }
    }

    public void update(final ArticleBean articleBean) {
        this.articleMapper.update(articleBean);
        if (articleBean instanceof MusicBean) {
            // this.musicMapper.update((MusicBean) articleBean);
        }
    }

    public void delete(final ArticleBean articleBean) {
        if (articleBean instanceof MusicBean) {
            this.musicMapper.delete(articleBean);
        }
    }

    public int countMusic(final Map<String, Object> params) {
        return this.musicMapper.countForSearch(params);
    }

    public List<MusicBean> searchMusic(final Map<String, Object> params) {
        return this.musicMapper.fetchListForSearch(params);
    }

    public void addTag(final ArticleBean articleBean, final TagBean tagBean) {
        final ArticleTagBean articleTagBean = new ArticleTagBean();
        articleTagBean.setArticleBean(articleBean);
        articleTagBean.setTagBean(tagBean);
        this.articleTagMapper.insert(articleTagBean);
    }

    public List<String> getTags(final ArticleBean articleBean) {
        final Map<String, Object> param = new HashMap<>();
        param.put("articleBean", articleBean);
        return this.articleTagMapper.fetchTags(param);
    }

    public void removeTags(final ArticleBean articleBean) {
        final Map<String, Object> param = new HashMap<>();
        param.put("articleBean", articleBean);
        this.articleTagMapper.delete(param);
    }

    public MusicBean getMusicById(final Integer id) {
        final Map<String, Object> param = new HashMap<>();
        param.put("id", id);
        return this.musicMapper.fetchBean(param);
    }

    public boolean validateTitle(final String title,
                                 final String errorAttribute,
                                 final Map<String, Object> model,
                                 final Locale locale) {
        boolean isValid = true;
        final String fieldName = this.messageSource.getMessage("ArticleBean.title",
                                                               null,
                                                               locale);
        if (StringUtils.isBlank(title)) {
            model.put(errorAttribute,
                      this.messageSource.getMessage("validate.empty",
                                                    new Object[] { fieldName },
                                                    locale));
            isValid = false;
        } else if (StringUtils.length(title) > AritcleManager.TITLE_LENGTH) {
            model.put(errorAttribute,
                      this.messageSource.getMessage("validate.tooLong",
                                                    new Object[] { fieldName,
                                                                  AritcleManager.TITLE_LENGTH },
                                                    locale));
            isValid = false;
        }
        return isValid;
    }
}