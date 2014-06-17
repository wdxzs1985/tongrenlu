package info.tongrenlu.persistence;

import info.tongrenlu.domain.FileBean;

import java.util.List;
import java.util.Map;


public interface MFileMapper {

    public void insertFile(FileBean fileBean);

    public List<FileBean> getArticleFiles(Map<String, Object> param);

    public FileBean getFileInfo(Map<String, Object> param);

    public void updateFileOrder(FileBean fileBean);

    public void deleteFileInfo(FileBean fileBean);

}
