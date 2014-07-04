package info.tongrenlu.service;

import info.tongrenlu.entity.DtoBean;

import java.util.List;
import java.util.Map;

import org.apache.http.MethodNotSupportedException;
import org.springframework.stereotype.Component;

@Component
public class ObjectRepository extends RepositorySupport implements Repository<DtoBean, String> {

    @Override
    public void save(final DtoBean objectBean) {
        final DtoBean d = this.findOne(objectBean.getObjectId());
        if (d == null) {
            this.insert(objectBean);
        } else {
            this.update(objectBean);
        }
    }

    @Override
    public void insert(final DtoBean objectBean) {
        final String sql = "INSERT INTO " + "m_object ("
                + "object_id,"
                + "mysql_id"
                + ") VALUES ("
                + "?,?"
                + ")";
        this.getMysqlDao().update(sql,
                                  objectBean.getObjectId(),
                                  objectBean.getId());
    }

    @Override
    public void update(final DtoBean objectBean) {
        final String sql = "UPDATE " + "m_object "
                + "SET "
                + "mysql_id = ?"
                + " where "
                + "object_id = ?";
        this.getMysqlDao().update(sql,
                                  objectBean.getId(),
                                  objectBean.getObjectId());
    }

    @Override
    public List<DtoBean> findAll() {
        throw new RuntimeException(new MethodNotSupportedException(""));
    }

    @Override
    public DtoBean findOne(final String id) {
        try {
            final String sql = "SELECT " + "object_id as objectId, "
                    + " mysql_id as id   "
                    + "FROM "
                    + " m_object "
                    + "where "
                    + " object_id = ?";
            this.log.info("[sql] = " + sql);
            this.log.info("[object_id] = " + id);

            final Map<String, Object> result = this.getMysqlDao()
                                                   .queryForMap(sql, id);
            final DtoBean objectBean = new DtoBean();
            objectBean.setId((Integer) result.get("id"));
            objectBean.setObjectId((String) result.get("objectId"));

            return objectBean;
        } catch (final Exception e) {
            this.log.info("[exception] = " + e.getMessage());
            return null;
        }
    }

    @Override
    public void deleteAll() {
        this.deleteAll("m_object");
    }

    @Override
    public void deleteOne(final String id) {
        throw new RuntimeException(new MethodNotSupportedException(""));

    }
}
