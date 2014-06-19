package info.tongrenlu.service.dao;

import info.tongrenlu.support.PaginateSupport;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class TimelineDao {

    // @Autowired
    // private MTimelineMapper timelineMapper = null;

    public PaginateSupport getUserTimeline(final String userId,
                                           final PaginateSupport paginate) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("userId", userId);

        // final int itemCount = this.timelineMapper.countTimeline(param);
        // paginate.setItemCount(itemCount);
        paginate.compute();

        param.put("start", paginate.getStart());
        param.put("end", paginate.getEnd());
        // final List<TimelineBean> items =
        // this.timelineMapper.fetchTimelineList(param);
        // paginate.setItems(items);
        return paginate;
    }

    public PaginateSupport getMyTimeline(final String userId,
                                         final PaginateSupport paginate) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("userId", userId);
        param.put("loginUserId", userId);

        // final int itemCount = this.timelineMapper.countTimeline(param);
        // paginate.setItemCount(itemCount);
        paginate.compute();

        param.put("start", paginate.getStart());
        param.put("end", paginate.getEnd());
        // final List<TimelineBean> items =
        // this.timelineMapper.fetchTimelineList(param);
        // paginate.setItems(items);
        return paginate;
    }
}
