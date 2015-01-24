package info.tongrenlu.service;

import info.tongrenlu.domain.UserBean;
import info.tongrenlu.domain.UserProfileBean;
import info.tongrenlu.manager.UserManager;
import info.tongrenlu.support.PaginateSupport;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminUserService {

    @Autowired
    private UserManager userManager;

    public void searchUser(PaginateSupport<UserBean> paginate) {
        final int itemCount = this.userManager.countUser(paginate.getParams());
        paginate.setItemCount(itemCount);
        paginate.compute();

        final List<UserBean> items = this.userManager.searchUser(paginate.getParams());
        paginate.setItems(items);
    }

    public UserProfileBean getById(Integer userId) {
        return this.userManager.getById(userId);
    }

}
