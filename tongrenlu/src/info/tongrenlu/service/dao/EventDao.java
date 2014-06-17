package info.tongrenlu.service.dao;

import info.tongrenlu.domain.EventBean;
import info.tongrenlu.persistence.MEventMapper;
import info.tongrenlu.support.PaginateSupport;
import info.tongrenlu.support.SequenceSupport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;


@Component
public class EventDao extends SequenceSupport {

    @Autowired
    private MessageSource messageSource = null;
    @Autowired
    private MEventMapper eventMapper = null;

    public PaginateSupport getEventList(final PaginateSupport paginate) {
        final Map<String, Object> param = new HashMap<String, Object>();
        final int itemCount = this.eventMapper.countEvent(param);
        paginate.setItemCount(itemCount);
        paginate.compute();

        param.put("order", "A.EVENT_ID DESC");
        param.put("start", paginate.getStart());
        param.put("end", paginate.getEnd());
        final List<EventBean> items = this.eventMapper.fetchEventList(param);
        paginate.setItems(items);
        return paginate;
    }

    public boolean validateCreateEvent(final EventBean event, final Model model) {
        boolean isValid = true;
        if (StringUtils.isBlank(event.getEventName())) {
            model.addAttribute("eventName_error",
                               this.messageSource.getMessage("EventBean.eventName[Blank]",
                                                       null,
                                                       null));
            isValid = false;
        } else if (StringUtils.length(event.getEventName()) > 20) {
            model.addAttribute("eventName_error",
                               this.messageSource.getMessage("EventBean.eventName[TooLong]",
                                                       new Integer[] { 20 },
                                                       null));
            isValid = false;
        }
        return isValid;
    }

    public void createEvent(final EventBean event, final Model model) {
        event.setEventId(this.getNextId());
        this.eventMapper.insertEvent(event);
    }

    public EventBean getComingEvent() {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("comingFlg", "1");
        param.put("order", "A.EVENT_DATE");
        param.put("start", 1);
        param.put("end", 1);
        final List<EventBean> items = this.eventMapper.fetchEventList(param);
        if (CollectionUtils.isEmpty(items)) {
            return null;
        } else {
            return items.get(0);
        }
    }

}
