package info.tongrenlu.manager;

import info.tongrenlu.domain.NotificationBean;
import info.tongrenlu.domain.UserBean;
import info.tongrenlu.mapper.NotificationMapper;
import info.tongrenlu.mapper.UserMapper;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationManager {

    public static final Pattern USER_PATTERN = Pattern.compile("@.*?#([0-9]+)");

    private final Log log = LogFactory.getLog(this.getClass());

    @Autowired
    private NotificationMapper notificationMapper;
    @Autowired
    private UserMapper userMapper;

    public Collection<UserBean> findUserFromString(final String content) {
        final Collection<UserBean> users = new HashSet<UserBean>();
        final Matcher matcher = USER_PATTERN.matcher(content);
        while (matcher.find()) {
            final Integer id = Integer.valueOf(matcher.group(1));
            final Map<String, Object> param = new HashMap<String, Object>();
            param.put("id", id);
            final UserBean userBean = this.userMapper.fetchProfile(param);
            if (userBean != null) {
                if (this.log.isDebugEnabled()) {
                    this.log.debug(userBean);
                }
                users.add(userBean);
            }
        }
        return users;
    }

    public void sendNotification(final NotificationBean notificationBean) {
        this.notificationMapper.insert(notificationBean);
    }

}
