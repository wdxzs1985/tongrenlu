package info.tongrenlu.service;

import info.tongrenlu.domain.ComicBean;
import info.tongrenlu.domain.FileBean;
import info.tongrenlu.domain.UserBean;
import info.tongrenlu.manager.ArticleManager;
import info.tongrenlu.manager.FileManager;
import info.tongrenlu.manager.TagManager;
import info.tongrenlu.support.PaginateSupport;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class HomeComicService {

    @Autowired
    private ArticleManager articleManager = null;
    @Autowired
    private TagManager tagManager = null;

    public List<FileBean> getPictureList(final Integer articleId) {
        return this.articleManager.getFiles(articleId, FileManager.IMAGE);
    }

    public void searchComic(final PaginateSupport<ComicBean> paginate) {
        final int itemCount = this.articleManager.countComic(paginate.getParams());
        paginate.setItemCount(itemCount);
        paginate.compute();

        final List<ComicBean> items = this.articleManager.searchComic(paginate.getParams());
        paginate.setItems(items);
    }

    public ComicBean getById(final Integer id) {
        return this.articleManager.getComicById(id);
    }

    public String[] getTags(final ComicBean comicBean) {
        return this.articleManager.getTags(comicBean).toArray(new String[] {});
    }

    public void addAccess(final ComicBean comicBean, final UserBean loginUser) {
        this.articleManager.addAccess(comicBean, loginUser);
    }

}
