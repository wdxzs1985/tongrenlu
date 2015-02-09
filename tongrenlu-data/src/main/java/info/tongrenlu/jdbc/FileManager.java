package info.tongrenlu.jdbc;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class FileManager {

    @Autowired
    private JdbcTemplate mysqlDao;

    public List<Map<String, Object>> fetchList() {
        return this.mysqlDao.queryForList("select " + "m_file.id as id," + "m_file.article_Id as articleId," + "m_file.name as name," + "m_file.extension as extension," + "m_file.content_type as contentType," + "m_file.order_no as orderNo," + "m_file.checksum as checksum," + "m_file.upd_date as updDate," + "m_file.del_flg as delFlg" + " from m_file join v_music on m_file.article_id = v_music.id where m_file.del_flg = 0");
    }

    public void updateChecksum(Map<String, Object> file) {
        this.mysqlDao.update("update m_file set checksum = ? where id = ?",
                             file.get("checksum"),
                             file.get("id"));
    }
}
