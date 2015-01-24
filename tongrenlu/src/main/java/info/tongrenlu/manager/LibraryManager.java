package info.tongrenlu.manager;

import info.tongrenlu.domain.ArticleBean;
import info.tongrenlu.domain.MusicBean;
import info.tongrenlu.domain.UserBean;
import info.tongrenlu.domain.UserLibraryBean;
import info.tongrenlu.mapper.UserLibraryMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LibraryManager {

    @Autowired
    private UserLibraryMapper userLibraryMapper;

    public int countMusic(Map<String, Object> params) {
        return this.userLibraryMapper.count(params);
    }

    public List<MusicBean> searchMusic(final Map<String, Object> params) {
        return this.userLibraryMapper.searchMusicList(params);
    }

    public List<MusicBean> fetchMusicList(Map<String, Object> params) {
        return this.userLibraryMapper.fetchMusicList(params);
    }

    public boolean isOwner(final UserBean userBean,
                           final ArticleBean articleBean, Integer status) {
        final Map<String, Object> params = new HashMap<>();
        params.put("userBean", userBean);
        params.put("articleBean", articleBean);
        params.put("status", status);
        return this.userLibraryMapper.count(params) > 0;
    }

    public void addToLibrary(final UserBean userBean,
                             final ArticleBean articleBean, Integer status) {
        final UserLibraryBean userLibraryBean = new UserLibraryBean();
        userLibraryBean.setArticleBean(articleBean);
        userLibraryBean.setUserBean(userBean);
        userLibraryBean.setStatus(status);
        this.userLibraryMapper.insert(userLibraryBean);
    }

    public void updateStatus(final UserBean userBean,
                             final ArticleBean articleBean, Integer status) {
        final UserLibraryBean userLibraryBean = new UserLibraryBean();
        userLibraryBean.setArticleBean(articleBean);
        userLibraryBean.setUserBean(userBean);
        userLibraryBean.setStatus(status);
        this.userLibraryMapper.update(userLibraryBean);
    }

}
