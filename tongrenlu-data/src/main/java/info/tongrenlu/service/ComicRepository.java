package info.tongrenlu.service;

import info.tongrenlu.entity.ComicEntity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.MethodNotSupportedException;
import org.springframework.stereotype.Component;

@Component
public class ComicRepository extends RepositorySupport implements Repository<ComicEntity, Integer> {

    @Override
    public List<ComicEntity> findAll() {
        try {
            final String sql = "Select " + "ARTICLE_ID      as articleId"
                    + "        from R_Comic "
                    + "         where DEL_FLG = '0'";
            return this.getOracleDao().queryForList(sql, ComicEntity.class);
        } catch (final Exception e) {
            return this.dummy();
        }

    }

    private List<ComicEntity> dummy() {
        final List<ComicEntity> dummy = new ArrayList<ComicEntity>();
        return dummy;
    }

    @Override
    public ComicEntity findOne(final Integer id) {
        throw new RuntimeException(new MethodNotSupportedException(""));
    }

    @Override
    public void insert(final ComicEntity comic) {
        final String sql = "INSERT INTO " + "r_comic("
                + "id , red_flg, translate_flg"
                + ") VALUES ("
                + "?,?,?"
                + ")";
        this.getMysqlDao().update(sql,
                                  comic.getId(),
                                  comic.getRedFlg(),
                                  comic.getTranslateFlg());
    }

    @Override
    public void save(final ComicEntity comic) {
        this.insert(comic);
    }

    @Override
    public void deleteAll() {
        this.deleteAll("r_comic");
    }

    @Override
    public void deleteOne(final Integer id) {
        throw new RuntimeException(new MethodNotSupportedException(""));
    }

    @Override
    public void update(final ComicEntity bean) {
        throw new RuntimeException(new MethodNotSupportedException(""));
    }
}
