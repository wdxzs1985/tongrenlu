package info.tongrenlu.service;

import info.tongrenlu.domain.DtoBean;
import info.tongrenlu.domain.FileBean;
import info.tongrenlu.domain.MusicBean;
import info.tongrenlu.domain.TrackBean;
import info.tongrenlu.manager.FileManager;
import info.tongrenlu.support.PaginateSupport;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
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

    public FileBean createImageFileInfo(final String articleId,
                                        final MultipartFile fileItem) {
        // final FileBean fileBean = this.createFileInfo(articleId,
        // fileItem,
        // FileService.JPG);
        // final File inputFile = this.getJpgFile(fileBean.getArticleId(),
        // fileBean.getFileId());
        // this.saveUpload(fileItem, inputFile);
        // this.convertThumbnail(fileBean, inputFile);
        return null;
    }

    public void convertThumbnail(final FileBean fileBean, final File inputFile) {
        // for (final int size : FileService.COMIC_SIZE_ARRAY) {
        // // final String name = fileBean.getId() + "_" + size;
        // // final File outputFile = this.getJpgFile(fileBean.getArticleId(),
        // // name);
        // // this.convertComic(inputFile.getAbsolutePath(),
        // // outputFile.getAbsolutePath(),
        // // size);
        // }
    }

    // public File getMp3File(final String category,
    // final String dirId,
    // final String name) {
    // return this.getFile(dirId, name, FileService.MP3);
    // }

    public void deleteJpgFile(final FileBean fileBean) {
        // this.fileMapper.deleteFileInfo(fileBean);
        // final File originalFile = this.getJpgFile(fileBean.getArticleId(),
        // fileBean.getFileId());
        // FileUtils.deleteQuietly(originalFile);
        // for (final int size : FileService.COMIC_SIZE_ARRAY) {
        // final File thumbnail = this.getJpgFile(fileBean.getArticleId(),
        // fileBean.getFileId() + "_"
        // + size);
        // FileUtils.deleteQuietly(thumbnail);
        // }
    }

    public void deleteMp3File(final FileBean fileBean) {
        // this.fileMapper.deleteFileInfo(fileBean);
        // this.deleteTrack(fileBean.getFileId());
        // final File mp3File = this.getMp3File(fileBean.getArticleId(),
        // fileBean.getFileId());
        // FileUtils.deleteQuietly(mp3File);
    }

    protected void deleteTrack(final String fileId) {
        // final Map<String, Object> param = new HashMap<String, Object>();
        // param.put("fileId", fileId);
        // this.trackMapper.deleteTrack(param);
    }

    public List<FileBean> getArticleFiles(final String articleId,
                                          final String extension) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("articleId", articleId);
        param.put("extension", extension);
        // final List<FileBean> files = this.fileMapper.getArticleFiles(param);
        // return files;
        return Collections.emptyList();
    }

    public void sortFiles(final String[] fileIdArray) {
        int orderNo = 1;
        for (final String fileId : fileIdArray) {
            final FileBean fileInfo = new FileBean();
            // fileInfo.setId(fileId);
            fileInfo.setOrderNo(orderNo);
            // this.fileMapper.updateFileOrder(fileInfo);
            orderNo++;
        }
    }

    protected void convertComic(final String input,
                                final String output,
                                final int size) {
        // final ConvertCmd cmd = new ConvertCmd();
        // // cmd.setAsyncMode(true);
        // cmd.setSearchPath(this.getConvertPath());
        // // create the operation, add images and operators/options
        // final IMOperation op = new IMOperation();
        // op.density(72);
        // op.quality(80d);
        // op.addImage(input);
        // op.thumbnail(size, size, '>');
        // op.addImage(output);
        // // execute the operation
        // try {
        // cmd.run(op);
        // } catch (final IOException e) {
        // throw new RuntimeException(e);
        // } catch (final InterruptedException e) {
        // throw new RuntimeException(e);
        // } catch (final IM4JavaException e) {
        // throw new RuntimeException(e);
        // }
    }

    public List<TrackBean> getMusicTracks(final String articleId) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("articleId", articleId);
        // return this.trackMapper.getTrackList(param);
        return Collections.emptyList();
    }

    public FileBean getFileInfo(final String fileId) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("fileId", fileId);
        // return this.fileMapper.getFileInfo(param);
        return null;
    }

    public void updateTracks(final String[] fileIdArray,
                             final String[] songTitleArray,
                             final String[] leadArtistArray,
                             final String[] originalTitleArray,
                             final MusicBean music) {

        for (int i = 0; i < fileIdArray.length; i++) {
            final int orderNo = i + 1;
            final FileBean fileBean = new FileBean();
            // fileBean.setFileId(fileId);
            fileBean.setOrderNo(orderNo);
            // this.fileMapper.updateFileOrder(fileBean);

            final TrackBean trackBean = new TrackBean();
            trackBean.setFileBean(fileBean);
            // trackBean.setTrack(songTitleArray[i]);

            if (ArrayUtils.isNotEmpty(leadArtistArray)) {
                trackBean.setArtist(leadArtistArray[i]);
            }

            // if (ArrayUtils.isNotEmpty(originalTitleArray)) {
            // trackBean.setOriginalTitle(originalTitleArray[i]);
            // }
            // trackBean.setTrackNumber(orderNo);
            // this.trackMapper.updateTrackBean(trackBean);
        }
    }

    public Map<String, Object> prepareJpgFileBeanModel(final FileBean fileBean,
                                                       final HttpServletRequest request) {

        // final String serverName = request.getServerName();
        // final boolean isLocal = StringUtils.contains(serverName, "127.0.0.1")
        // || StringUtils.contains(serverName,
        // "192.168.11.");
        // final String FILE_PATH = isLocal ? "http://192.168.11.9/resource"
        // : "/resource";
        //
        final Map<String, Object> model = new HashMap<String, Object>();
        // model.put("name", fileBean.getName());
        // // model.put("size", fileBean.getSize());
        // model.put("url", FILE_PATH + "/"
        // + fileBean.getArticleBean().getId()
        // + "/"
        // + fileBean.getId()
        // + "_800.jpg");
        // model.put("thumbnailUrl", FILE_PATH + "/"
        // + fileBean.getArticleBean().getId()
        // + "/"
        // + fileBean.getId()
        // + "_120.jpg");
        // model.put("deleteUrl", request.getContextPath() + "/admin/file/"
        // + fileBean.getId()
        // + "/delete");
        // model.put("deleteType", "GET");
        return model;
    }

    public Map<String, Object> prepareMp3FileBeanModel(final FileBean fileBean,
                                                       final HttpServletRequest request) {
        // final String serverName = request.getServerName();
        // final boolean isLocal = StringUtils.contains(serverName, "127.0.0.1")
        // || StringUtils.contains(serverName,
        // "192.168.11.");
        // final String FILE_PATH = isLocal ? "http://192.168.11.9/resource"
        // : "/resource";
        //
        final Map<String, Object> model = new HashMap<String, Object>();
        // model.put("name", fileBean.getName());
        // // model.put("size", fileBean.getSize());
        // model.put("url", FILE_PATH + "/"
        // + fileBean.getArticleBean().getId()
        // + "/"
        // + fileBean.getId()
        // + ".mp3");
        // model.put("thumbnailUrl", FILE_PATH + "/"
        // + fileBean.getArticleBean().getId()
        // + "/cover_60.jpg");
        // model.put("deleteUrl", request.getContextPath() + "/admin/file/"
        // + fileBean.getId()
        // + "/delete");
        // model.put("deleteType", "GET");
        return model;
    }

    public PaginateSupport searchMusicTracks(final String searchQuery,
                                             final PaginateSupport paginate) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("searchQuery", searchQuery);
        // final int itemCount = this.trackMapper.getTrackCount(param);
        // paginate.setItemCount(itemCount);
        paginate.compute();
        param.put("start", paginate.getStart());
        // param.put("end", paginate.getEnd());
        // final List<TrackBean> items = this.trackMapper.getTrackList(param);
        // paginate.setItems(items);
        return paginate;
    }
}
