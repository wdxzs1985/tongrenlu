package info.tongrenlu.service;

import info.tongrenlu.domain.UserBean;
import info.tongrenlu.domain.UserProfileBean;
import info.tongrenlu.manager.UserManager;
import info.tongrenlu.support.PaginateSupport;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminUserService {

    private Log log = LogFactory.getLog(this.getClass());
    @Autowired
    private UserManager userManager;

    public void searchUser(final PaginateSupport<UserBean> paginate) {
        final int itemCount = this.userManager.countUser(paginate.getParams());
        paginate.setItemCount(itemCount);
        paginate.compute();

        final List<UserBean> items = this.userManager.searchUser(paginate.getParams());
        paginate.setItems(items);

    }

    public UserBean getById(final Integer userId) {
        return this.userManager.getById(userId);
    }

    public UserProfileBean getUserProfileById(final Integer userId) {
        return this.userManager.getUserProfileById(userId);
    }

    public void updateRole(final UserBean userBean) {
        this.userManager.updateRole(userBean);
    }

}
