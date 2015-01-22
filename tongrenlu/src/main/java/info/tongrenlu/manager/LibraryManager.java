package info.tongrenlu.manager;

import info.tongrenlu.domain.MusicBean;
import info.tongrenlu.mapper.UserLibraryMapper;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LibraryManager {

    @Autowired
    private UserLibraryMapper userLibraryMapper;

    public int countMusic(final Map<String, Object> params) {
        return this.userLibraryMapper.count(params);
    }

    public List<MusicBean> searchMusic(final Map<String, Object> params) {
        return this.userLibraryMapper.fetchList(params);
    }

    public void insert(final Map<String, Object> params) {
        this.userLibraryMapper.insert(params);
    }

}
