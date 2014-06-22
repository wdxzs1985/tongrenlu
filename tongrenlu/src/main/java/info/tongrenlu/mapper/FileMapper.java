package info.tongrenlu.mapper;

import info.tongrenlu.domain.FileBean;

import java.util.List;
import java.util.Map;

public interface FileMapper {

    public void insert(FileBean fileBean);

    public List<FileBean> fetchList(Map<String, Object> params);

    public FileBean fetchBean(Map<String, Object> params);

    public void delete(FileBean fileBean);

    public void update(Map<String, Object> params);

}
