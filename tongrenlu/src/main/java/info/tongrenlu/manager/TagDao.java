package info.tongrenlu.manager;

import info.tongrenlu.domain.ArticleBean;
import info.tongrenlu.domain.ArticleTagBean;
import info.tongrenlu.domain.TagBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TagDao {

    @Autowired
    private MessageSource messageSource = null;

    // @Autowired
    // private MTagMapper tagMapper = null;
    // @Autowired
    // private RArticleTagMapper articleTagMapper = null;

    public void addArticleTag(final ArticleBean articleBean,
                              final String[] tagIdArray) {
        if (ArrayUtils.isEmpty(tagIdArray)) {
            return;
        }
        for (final String tagId : tagIdArray) {
            final TagBean tagBean = new TagBean();
            // tagBean.setTagId(tagId);
            this.addArticleTag(articleBean, tagBean);
        }
    }

    public void addArticleTag(final ArticleBean articleBean,
                              final TagBean tagBean) {
        final ArticleTagBean articleTagBean = new ArticleTagBean();
        articleTagBean.setTagBean(tagBean);
        articleTagBean.setArticleBean(articleBean);
        // if (this.articleTagMapper.countArticleTag(articleTagBean) == 0) {
        // this.articleTagMapper.insertArticleTag(articleTagBean);
        // }
    }

    public boolean validateCreateTag(final String tag,
                                     final Map<String, Object> model) {
        boolean isValid = true;
        if (StringUtils.isBlank(tag)) {
            isValid = false;
            model.put("error",
                      this.messageSource.getMessage("TagBean.tag[Blank]",
                                                    null,
                                                    null));
        }
        return isValid;
    }

    public void createTag(final TagBean tagBean) {
        // this.tagMapper.insertTag(tagBean);
    }

    public List<TagBean> getArticleTag(final String articleId) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("articleId", articleId);
        // return this.articleTagMapper.fetchArticleTag(param);

        return Collections.emptyList();
    }

    @Transactional
    public void removeTag(final String articleId, final String tagId) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("articleId", articleId);
        param.put("tagId", tagId);
        // this.articleTagMapper.removeArticleTag(param);
    }

    public TagBean getTagBeanByTagId(final String tagId) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("tagId", tagId);
        // return this.tagMapper.fetchTag(param);
        return null;
    }

    public List<TagBean> resolveInputTagBeanList(final String[] tagIdArray,
                                                 final String[] tagArray) {
        final List<TagBean> tagBeanList = new ArrayList<TagBean>();
        if (ArrayUtils.isNotEmpty(tagIdArray) && ArrayUtils.isNotEmpty(tagArray)) {
            for (int i = 0; i < tagIdArray.length; i++) {
                final TagBean tagBean = new TagBean();
                // tagBean.setTagId(tagIdArray[i]);
                tagBean.setTag(tagArray[i]);
                tagBeanList.add(tagBean);
            }
        }
        return tagBeanList;
    }

    public List<TagBean> getTagListByTag(final String tag,
                                         final int start,
                                         final int end) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("tag", tag);
        param.put("start", start);
        param.put("end", end);
        // return this.tagMapper.fetchTagList(param);
        return Collections.emptyList();
    }
}
