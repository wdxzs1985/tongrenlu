package info.tongrenlu.manager;

import info.tongrenlu.domain.MusicBean;
import info.tongrenlu.mapper.ArticleMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AritcleManager {

    @Autowired
    private ArticleMapper articleMapper = null;

    public void create(final MusicBean inputMusic) {
        // TODO Auto-generated method stub

    }

    public void addArticleTag(final MusicBean inputMusic, final String[] tags) {
        // TODO Auto-generated method stub

    }

}