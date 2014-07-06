package info.tongrenlu.jdbc;

import info.tongrenlu.entity.FileEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.MethodNotSupportedException;
import org.springframework.stereotype.Component;

@Component
public class FileManager extends ManagerSupport implements
        Manager<FileEntity, Integer> {

    @Override
    public List<FileEntity> findAll() {
        final List<FileEntity> result = new ArrayList<FileEntity>();
        final String sql = "Select " + "FILE_ID      as fileId"
                           + "    ,   NAME                  as name"
                           + "    ,   EXTENSION                 as extension"
                           + "    ,   ARTICLE_ID                 as articleId"
                           + "    ,   ORDER_NO                   as orderNo"
                           + "        from M_FILE "
                           + "         where DEL_FLG = '0'";
        final List<Map<String, Object>> resultList = this.getOracleDao()
                                                         .queryForList(sql);
        for (final Map<String, Object> map : resultList) {
            result.add(this.mapToBean(map));
        }
        return result;

    }

    public List<FileEntity> findByArticleId(final String articleId) {
        final List<FileEntity> result = new ArrayList<FileEntity>();
        final String sql = "Select " + "FILE_ID      as fileId"
                           + "    ,   NAME                  as name"
                           + "    ,   EXTENSION                 as extension"
                           + "    ,   ARTICLE_ID                 as articleId"
                           + "    ,   ORDER_NO                   as orderNo"
                           + "        from M_FILE "
                           + "  where   "
                           + "      ARTICLE_ID = ?  "
                           + "  and DEL_FLG = '0'";
        this.log.info("[sql] = " + sql);
        this.log.info("[id] = " + articleId);
        final List<Map<String, Object>> resultList = this.getOracleDao()
                                                         .queryForList(sql,
                                                                       articleId);
        for (final Map<String, Object> map : resultList) {
            result.add(this.mapToBean(map));
        }
        return result;
    }

    private FileEntity mapToBean(final Map<String, Object> map) {
        return new FileEntity((String) map.get("fileId"),
                              (String) map.get("name"),
                              (String) map.get("extension"),
                              (String) map.get("articleId"),
                              ((BigDecimal) map.get("orderNo")).intValue());
    }

    @Override
    public FileEntity findOne(final Integer id) {
        final String sql = "Select " + "id      as id"
                           + "        from m_file "
                           + "     where "
                           + "             id = ?";
        this.log.info("[sql] = " + sql);
        this.log.info("[id] = " + id);
        final Map<String, Object> result = this.getMysqlDao().queryForMap(sql,
                                                                          id);
        final FileEntity bean = new FileEntity();
        bean.setId((Integer) result.get("id"));
        return bean;
    }

    @Override
    public void insert(final FileEntity file) {
        final String sql = "INSERT INTO " + "m_file("
                           + "article_id,"
                           + "name,"
                           + "extension,"
                           + "content_type,"
                           + "order_no"
                           + ") VALUES ("
                           + "?,?,?,?,?"
                           + ")";
        this.getMysqlDao().update(sql,
                                  file.getArticle().getId(),
                                  file.getName(),
                                  file.getExtension(),
                                  file.getContentType(),
                                  file.getOrderNo());
        file.setId(this.findId());
    }

    @Override
    public void save(final FileEntity file) {
        this.insert(file);
    }

    @Override
    public void deleteAll() {
        this.deleteAll("m_file");
    }

    @Override
    public void deleteOne(final Integer id) {
        throw new RuntimeException(new MethodNotSupportedException(""));
    }

    @Override
    public void update(final FileEntity bean) {
        throw new RuntimeException(new MethodNotSupportedException(""));
    }
}
