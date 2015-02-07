package info.tongrenlu.manager;

import info.tongrenlu.domain.ArticleBean;
import info.tongrenlu.domain.AuthFileBean;
import info.tongrenlu.domain.MusicBean;
import info.tongrenlu.domain.UserBean;
import info.tongrenlu.domain.UserLibraryBean;
import info.tongrenlu.domain.UserProfileBean;
import info.tongrenlu.mapper.AuthFileMapper;
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
    @Autowired
    private AuthFileMapper authFileMapper;

    public int countMusic(final Map<String, Object> params) {
        return this.userLibraryMapper.countMusic(params);
    }

    public List<MusicBean> searchMusic(final Map<String, Object> params) {
        return this.userLibraryMapper.searchMusicList(params);
    }

    public boolean isOwner(final UserBean userBean,
                           final ArticleBean articleBean, final Integer status) {
        final Map<String, Object> params = new HashMap<>();
        params.put("userBean", userBean);
        params.put("articleBean", articleBean);
        params.put("status", status);
        return this.userLibraryMapper.countMusic(params) > 0;
    }

    public void addToLibrary(final UserBean userBean,
                             final ArticleBean articleBean, final Integer status) {
        final UserLibraryBean userLibraryBean = new UserLibraryBean();
        userLibraryBean.setArticleBean(articleBean);
        userLibraryBean.setUserBean(userBean);
        userLibraryBean.setStatus(status);
        this.userLibraryMapper.insert(userLibraryBean);
    }

    public void updateStatus(final UserBean userBean,
                             final ArticleBean articleBean, final Integer status) {
        final UserLibraryBean userLibraryBean = new UserLibraryBean();
        userLibraryBean.setArticleBean(articleBean);
        userLibraryBean.setUserBean(userBean);
        userLibraryBean.setStatus(status);
        this.userLibraryMapper.update(userLibraryBean);
    }

    public List<AuthFileBean> fetchAuthFileList(final UserBean userBean) {
        final Map<String, Object> params = new HashMap<>();
        params.put("userBean", userBean);
        return this.authFileMapper.fetchList(params);
    }

    public void insertAuthFile(final AuthFileBean authFileBean) {
        this.authFileMapper.insert(authFileBean);
    }

    public AuthFileBean getAuthFile(final Integer authFileId) {
        final Map<String, Object> params = new HashMap<>();
        params.put("authFileId", authFileId);
        return this.authFileMapper.fetchBean(params);
    }

    public void deleteAuthFile(final AuthFileBean authFileBean) {
        this.authFileMapper.delete(authFileBean);
    }

    public int countUser(final Map<String, Object> params) {
        return this.authFileMapper.countUser(params);
    }

    public List<UserProfileBean> searchUser(final Map<String, Object> params) {
        return this.authFileMapper.searchUserList(params);
    }

    public void updateStatus(final Integer authFileId, int status) {
        final Map<String, Object> params = new HashMap<>();
        params.put("authFileId", authFileId);
        params.put("status", status);
        this.authFileMapper.updateStatus(params);
    }

}
