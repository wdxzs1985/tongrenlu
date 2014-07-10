package info.tongrenlu;

import info.tongrenlu.jdbc.AccessManager;
import info.tongrenlu.jdbc.ArticleManager;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccessCounter {

    @Autowired
    AccessManager accessManager = null;
    @Autowired
    ArticleManager articleManager = null;

    public void doSerivce() {

        final List<Map<String, Object>> list = this.accessManager.countByArticleId();

        for (final Map<String, Object> map : list) {

            this.articleManager.updateAccessCnt(map);
        }

    }
}
