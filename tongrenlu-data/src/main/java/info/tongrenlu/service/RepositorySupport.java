package info.tongrenlu.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public abstract class RepositorySupport {

    @Autowired
    private JdbcTemplate oracleDao;

    @Autowired
    private JdbcTemplate mysqlDao;

    protected Log log = LogFactory.getLog(this.getClass());

    protected int findId() {
        final String sql = "SELECT @@IDENTITY";
        return this.getMysqlDao().queryForObject(sql, Integer.class);
    }

    protected void deleteAll(final String table) {
        final String sql = "DELETE FROM  " + table;
        this.getMysqlDao().update(sql);
    }

    public JdbcTemplate getOracleDao() {
        return this.oracleDao;
    }

    public void setOracleDao(final JdbcTemplate oracleDao) {
        this.oracleDao = oracleDao;
    }

    public JdbcTemplate getMysqlDao() {
        return this.mysqlDao;
    }

    public void setMysqlDao(final JdbcTemplate mysqlDao) {
        this.mysqlDao = mysqlDao;
    }

}
