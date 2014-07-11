package info.tongrenlu.jdbc;

import info.tongrenlu.entity.ArticleEntity;

import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class ArticleManagerMysql extends ManagerSupport {

    public ArticleEntity findOne(final Integer id) {
        final String sql = "Select " + "id      as id"
                           + "    ,   TITLE         as title"
                           + "    ,   DESCRIPTION       as description"
                           + "        from "
                           + "   m_article "
                           + "         where "
                           + "   id = ?"
                           + "  and  DEL_FLG = '0'";
        final Map<String, Object> map = this.getMysqlDao().queryForMap(sql);
        return this.mapToBean(map);
    }

    private ArticleEntity mapToBean(final Map<String, Object> map) {
        final ArticleEntity entity = new ArticleEntity();
        entity.setId((Integer) map.get("id"));
        entity.setTitle((String) map.get("title"));
        entity.setDescription((String) map.get("description"));
        return entity;
    }

}
