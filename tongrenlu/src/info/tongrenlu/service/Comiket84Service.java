package info.tongrenlu.service;

import info.tongrenlu.domain.UserBean;
import info.tongrenlu.service.dao.CircleMapDao;
import info.tongrenlu.service.dao.UserDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

@Service
@Transactional
public class Comiket84Service {

    public static final String EVENT_CODE = "C84";

    @Autowired
    private CircleMapDao circleMapDao = null;
    @Autowired
    private UserDao userDao = null;

    public String doGetIndex(final Model model) {
        final List<Object> circleCountList = this.circleMapDao.getCircleCountByArea(Comiket84Service.EVENT_CODE);
        model.addAttribute("circleCountList", circleCountList);
        return "special/c84/index";
    }

    public Map<String, Object> doGetCircle(final UserBean loginUser,
                                           final String searchArea) {
        String userId = null;
        if (loginUser != null) {
            userId = loginUser.getUserId();
        }
        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("result", false);
        model.put("searchArea", searchArea);
        model.put("items",
                  this.circleMapDao.getCircleList(Comiket84Service.EVENT_CODE,
                                                  searchArea,
                                                  userId));
        model.put("result", true);

        return model;
    }

    public Map<String, Object> doGetLike(final UserBean loginUser) {
        String userId = null;
        if (loginUser != null) {
            userId = loginUser.getUserId();
        }
        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("result", false);

        if (this.userDao.validateUserOnline(loginUser, model)) {
            model.put("items",
                      this.circleMapDao.getCircleLikeList(Comiket84Service.EVENT_CODE,
                                                          userId));
            model.put("result", true);

        }
        return model;
    }

    public Map<String, Object> doPostLike(final UserBean loginUser,
                                          final String circleId) {
        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("result", false);
        if (this.userDao.validateUserOnline(loginUser, model)) {
            final String userId = loginUser.getUserId();
            final int count = this.circleMapDao.countCircleByUser(userId,
                                                                  circleId);
            if (count == 0) {
                model.put("like", true);
                this.circleMapDao.createLike(userId, circleId);
            } else {
                model.put("like", false);
                this.circleMapDao.removeLike(userId, circleId);
            }
            model.put("result", true);
        }
        return model;
    }
}
