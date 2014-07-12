package info.tongrenlu.mapper;

import info.tongrenlu.domain.DtoBean;

import java.util.Map;

public interface ObjectMapper {

    DtoBean fetchBean(Map<String, Object> params);

}
