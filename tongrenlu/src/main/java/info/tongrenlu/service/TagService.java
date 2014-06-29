package info.tongrenlu.service;

import info.tongrenlu.domain.ComicBean;
import info.tongrenlu.domain.MusicBean;
import info.tongrenlu.domain.TagBean;
import info.tongrenlu.manager.TagManager;
import info.tongrenlu.support.PaginateSupport;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TagService {

    @Autowired
    private TagManager tagManager = null;

    public void searchTag(final PaginateSupport<TagBean> paginate) {
        final int itemCount = this.tagManager.countTag(paginate.getParams());
        paginate.setItemCount(itemCount);
        paginate.compute();

        final List<TagBean> items = this.tagManager.searchTag(paginate.getParams());
        paginate.setItems(items);
    }

    public TagBean getTagByTag(final String tag) {
        return this.tagManager.getByTag(tag);
    }

    public void searchMusicByTag(final PaginateSupport<MusicBean> paginate) {
        final int itemCount = this.tagManager.countMusic(paginate.getParams());
        paginate.setItemCount(itemCount);
        paginate.compute();

        final List<MusicBean> items = this.tagManager.searchMusic(paginate.getParams());
        paginate.setItems(items);
    }

    public void searchComicByTag(final PaginateSupport<ComicBean> paginate) {
        final int itemCount = this.tagManager.countComic(paginate.getParams());
        paginate.setItemCount(itemCount);
        paginate.compute();

        final List<ComicBean> items = this.tagManager.searchComic(paginate.getParams());
        paginate.setItems(items);

    }

}
