package info.tongrenlu.service;

import info.tongrenlu.entity.FileEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.MethodNotSupportedException;
import org.springframework.stereotype.Component;

@Component
public class FileRepository extends RepositorySupport implements Repository<FileEntity, Integer> {

    @Override
    public List<FileEntity> findAll() {
        try {
            final String sql = "Select " + "FILE_ID      as fileId"
                    + "    ,   NAME                  as name"
                    + "    ,   EXTENSION                 as extension"
                    + "    ,   ARTICLE_ID                 as articleId"
                    + "    ,   ORDER_NO                   as orderNo"
                    + "        from M_FILE "
                    + "         where DEL_FLG = '0'";
            return this.getOracleDao().queryForList(sql, FileEntity.class);
        } catch (final Exception e) {
            return this.dummy();
        }

    }

    private List<FileEntity> dummy() {
        final List<FileEntity> dummy = new ArrayList<FileEntity>();
        dummy.add(new FileEntity(0, "201406301269972",// FILE_ID
                                 "Salvation",// NAME
                                 "mp3",// EXTENSION
                                 "201406301269970",// ARTICLE_ID
                                 1));
        dummy.add(new FileEntity(0, "201406301269973",// FILE_ID
                                 "Muppet Puppeteer (Goliath form mix)",// NAME
                                 "mp3",// EXTENSION
                                 "201406301269970",// ARTICLE_ID
                                 2));
        dummy.add(new FileEntity(0, "201406301269974",// FILE_ID
                                 "Volatilia ad Purpurae",// NAME
                                 "mp3",// EXTENSION
                                 "201406301269970",// ARTICLE_ID
                                 3));
        dummy.add(new FileEntity(0, "201406301269975",// FILE_ID
                                 "Laevatein -Bloody Crusade-",// NAME
                                 "mp3",// EXTENSION
                                 "201406301269970",// ARTICLE_ID
                                 4));
        dummy.add(new FileEntity(0, "201406301269976",// FILE_ID
                                 "Gungnir -Annihilation-",// NAME
                                 "mp3",// EXTENSION
                                 "201406301269970",// ARTICLE_ID
                                 5));
        return dummy;
    }

    @Override
    public FileEntity findOne(final Integer id) {
        try {
            final String sql = "Select " + "id      as id"
                    + "        from M_file "
                    + "     where "
                    + "             id = ?";
            this.log.info("[sql] = " + sql);
            this.log.info("[id] = " + id);
            final Map<String, Object> result = this.getMysqlDao()
                                                   .queryForMap(sql, id);
            final FileEntity file = new FileEntity();
            file.setId((Integer) result.get("id"));
            return file;
        } catch (final Exception e) {
            return null;
        }
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
