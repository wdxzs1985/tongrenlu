package info.tongrenlu.jdbc;

import info.tongrenlu.entity.TagEntity;

import java.util.List;
import java.util.Map;

import org.apache.http.MethodNotSupportedException;
import org.springframework.stereotype.Component;

@Component
public class TagManager extends ManagerSupport implements
        Manager<TagEntity, Integer> {

    @Override
    public List<TagEntity> findAll() {
        throw new RuntimeException(new MethodNotSupportedException(""));
    }

    public TagEntity findByTagId(final String tagId) {
        final String sql = "Select " + "TAG_ID      as tagId"
                           + "    ,   TAG                  as tag"
                           + "        from M_TAG "
                           + "  where   "
                           + "      TAG_ID = ?  "
                           + "  and DEL_FLG = '0'";
        this.log.info("[sql] = " + sql);
        this.log.info("[id] = " + tagId);
        final Map<String, Object> map = this.getOracleDao().queryForMap(sql,
                                                                        tagId);
        return this.mapToBean(map);
    }

    private TagEntity mapToBean(final Map<String, Object> map) {
        return new TagEntity((String) map.get("tagId"), (String) map.get("tag"));
    }

    @Override
    public TagEntity findOne(final Integer id) {
        final String sql = "Select " + "id      as id"
                           + "        from m_tag "
                           + "     where "
                           + "             id = ?";
        this.log.info("[sql] = " + sql);
        this.log.info("[id] = " + id);
        final Map<String, Object> result = this.getMysqlDao().queryForMap(sql,
                                                                          id);
        final TagEntity bean = new TagEntity();
        bean.setId((Integer) result.get("id"));
        return bean;
    }

    @Override
    public void deleteAll() {
        this.deleteAll("m_tag");

    }

    @Override
    public void deleteOne(final Integer id) {
        throw new RuntimeException(new MethodNotSupportedException(""));

    }

    @Override
    public void save(final TagEntity bean) {
        this.insert(bean);

    }

    @Override
    public void insert(final TagEntity bean) {
        final String sql = "INSERT INTO " + "m_tag("
                           + "tag"
                           + ") VALUES ("
                           + "?"
                           + ")";
        this.getMysqlDao().update(sql, bean.getTag());
        bean.setId(this.findId());
    }

    @Override
    public void update(final TagEntity bean) {
        throw new RuntimeException(new MethodNotSupportedException(""));

    }

}
