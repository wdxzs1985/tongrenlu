package info.tongrenlu;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

@Component
public class EventPreviewDataService implements CommandLineRunner {

    @Autowired
    private JdbcTemplate mysqlDao;

    Log log = LogFactory.getLog(this.getClass());

    @Override
    public void run(final String... arg0) throws Exception {

        List<Map<String, Object>> musicList = this.findMusicByTag(2627);

        Gson gson = new Gson();
        String data = gson.toJson(musicList);
        this.log.debug(data);

        FileUtils.writeStringToFile(new File("c88.json"), data);
    }

    private List<Map<String, Object>> findMusicByTag(int tagId) {
        StringBuilder sql = new StringBuilder();
        sql.append("select v_music.id as id,");
        sql.append("v_music.title as title ");
        sql.append("from r_article_tag ");
        sql.append("JOIN v_music  ");
        sql.append("ON r_article_tag.article_id = v_music.id ");
        sql.append("where ");
        sql.append("r_article_tag.tag_id = ? ");
        sql.append("AND r_article_tag.del_flg = '0' ");
        sql.append("AND v_music.publishFlg in ('1', '2') ");
        sql.append("order by v_music.accessCount desc ");

        return this.mysqlDao.queryForList(sql.toString(), tagId);
    }
}
