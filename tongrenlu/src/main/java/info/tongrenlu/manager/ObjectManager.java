package info.tongrenlu.manager;

import info.tongrenlu.domain.DtoBean;
import info.tongrenlu.mapper.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ObjectManager {

    @Autowired
    ObjectMapper objectMapper = null;

    public DtoBean findByObjectId(final String objectId) {
        final Map<String, Object> params = new HashMap<>();
        params.put("objectId", objectId);
        return this.objectMapper.fetchBean(params);
    }

}
