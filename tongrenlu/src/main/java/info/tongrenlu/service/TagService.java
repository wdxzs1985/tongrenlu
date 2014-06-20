package info.tongrenlu.service;

import info.tongrenlu.constants.RedFlg;
import info.tongrenlu.constants.TranslateFlg;
import info.tongrenlu.domain.TagBean;
import info.tongrenlu.domain.UserBean;
import info.tongrenlu.manager.ComicDao;
import info.tongrenlu.manager.MusicDao;
import info.tongrenlu.manager.TagDao;
import info.tongrenlu.mapper.TagMapper;
import info.tongrenlu.support.PaginateSupport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

@Service
@Transactional
public class TagService {

    @Autowired
    private TagMapper tagMapper = null;

    public List<TagBean> getTagListByTag(final String tag,
                                         final int start,
                                         final int size) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("tag", tag);
        param.put("start", start);
        param.put("limit", size);
        return this.tagMapper.fetchListByTag(param);
    }

    @Autowired
    private ComicDao comicDao = null;
    @Autowired
    private MusicDao musicDao = null;
    @Autowired
    private TagDao tagDao = null;

    public Map<String, Object> doPostInputTag(final String tag) {
        final Map<String, Object> model = new HashMap<String, Object>();
        if (this.tagDao.validateCreateTag(tag, model)) {
            final List<TagBean> tagBeans = this.tagDao.getTagListByTag(tag,
                                                                       1,
                                                                       Integer.MAX_VALUE);
            if (CollectionUtils.isEmpty(tagBeans)) {
                final TagBean tagBean = new TagBean();
                tagBean.setTag(tag);
                this.tagDao.createTag(tagBean);

                tagBeans.add(tagBean);
            }
            model.put("tagBeanList", tagBeans);
        }
        return model;
    }

    public List<String> doGetSearchTag(final String tag) {
        if (StringUtils.isBlank(tag)) {
            return Collections.emptyList();
        }
        final List<String> tagList = new ArrayList<String>();
        final List<TagBean> tagBeanList = this.tagDao.getTagListByTag(tag,
                                                                      1,
                                                                      10);
        for (final TagBean tagBean : tagBeanList) {
            tagList.add(tagBean.getTag());
        }
        return tagList;
    }

    public String doGetView(final UserBean loginUser,
                            final String tagId,
                            final Model model) {
        final TagBean tagBean = this.tagDao.getTagBeanByTagId(tagId);
        if (tagBean == null) {
            return "home/error/404";
        }
        model.addAttribute(tagBean);
        final String redFlg = RedFlg.NOT_RED;
        final String translateFlg = TranslateFlg.NOT_TRANSLATED;
        if (loginUser != null) {
            // redFlg = loginUser.getRedFlg();
            // translateFlg = loginUser.getTranslateFlg();
        }
        model.addAttribute("comicLastestByTag",
                           this.comicDao.getComicLastest(null,
                                                         tagId,
                                                         redFlg,
                                                         translateFlg,
                                                         12));
        model.addAttribute("musicLastestByTag",
                           this.musicDao.getMusicLastest(null, tagId, 12));
        return "home/tag/view";
    }

    public String doGetTagComic(final UserBean loginUser,
                                final String tagId,
                                final Integer page,
                                final Model model) {
        final TagBean tagBean = this.tagDao.getTagBeanByTagId(tagId);
        if (tagBean == null) {
            return "home/error/404";
        }
        model.addAttribute(tagBean);

        final String redFlg = RedFlg.NOT_RED;
        final String translateFlg = TranslateFlg.NOT_TRANSLATED;
        final String userId = null;
        if (loginUser != null) {
            // redFlg = loginUser.getRedFlg();
            // translateFlg = loginUser.getTranslateFlg();
            // userId = loginUser.getUserId();
        }

        final PaginateSupport paginate = new PaginateSupport();
        paginate.setPage(page);
        paginate.setSize(10);
        model.addAttribute("page", this.comicDao.getComicList(null,
                                                              tagId,
                                                              redFlg,
                                                              translateFlg,
                                                              userId,
                                                              paginate));
        model.addAttribute("access10Comic",
                           this.comicDao.getComicAccess(redFlg,
                                                        translateFlg,
                                                        20));
        return "home/tag/comic";
    }

    public String doGetTagMusic(final UserBean loginUser,
                                final String tagId,
                                final Integer page,
                                final Model model) {
        final TagBean tagBean = this.tagDao.getTagBeanByTagId(tagId);
        if (tagBean == null) {
            return "home/error/404";
        }
        model.addAttribute(tagBean);

        final String userId = null;
        if (loginUser != null) {
            // userId = loginUser.getUserId();
        }

        final PaginateSupport paginate = new PaginateSupport();
        paginate.setPage(page);
        paginate.setSize(10);
        model.addAttribute("page", this.musicDao.getMusicList(null,
                                                              tagId,
                                                              userId,
                                                              paginate));
        model.addAttribute("access10Music", this.musicDao.getMusicAccess(20));
        return "home/tag/music";
    }

}
