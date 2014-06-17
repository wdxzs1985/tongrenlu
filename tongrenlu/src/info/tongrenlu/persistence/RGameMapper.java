package info.tongrenlu.persistence;

import info.tongrenlu.domain.GameBean;

import java.util.List;
import java.util.Map;


public interface RGameMapper {

    public void insertGame(GameBean gameBean);

    public int getGameCount(Map<String, Object> param);

    public List<GameBean> getGameList(Map<String, Object> param);

    public GameBean getGame(Map<String, Object> param);

    public void deleteGame(Map<String, Object> param);

    public void updateGame(Map<String, Object> param);

}
