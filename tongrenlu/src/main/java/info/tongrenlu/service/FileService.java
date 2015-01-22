package info.tongrenlu.service;

import info.tongrenlu.domain.ArticleBean;
import info.tongrenlu.domain.DtoBean;
import info.tongrenlu.manager.FileManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileService {

    @Autowired
    private FileManager fileManager = null;

    public void saveCover(final DtoBean dtoBean, final MultipartFile fileItem) {
        this.fileManager.saveCover(dtoBean, fileItem);
    }

    public void saveXFD(final ArticleBean articleBean, final MultipartFile fileItem) {
        this.fileManager.saveXFD(articleBean, fileItem);
    }

}
