package info.tongrenlu.service;

import info.tongrenlu.domain.EventBean;
import info.tongrenlu.service.dao.EventDao;
import info.tongrenlu.support.PaginateSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;


@Service
@Transactional
public class EventService {

    @Autowired
    private EventDao eventDao = null;

    public String doGetIndex(final Integer page, final Model model) {
        final PaginateSupport paginate = new PaginateSupport();
        paginate.setPage(page);
        paginate.setSize(10);
        model.addAttribute(this.eventDao.getEventList(paginate));
        return "admin/event";
    }

    public String doPostInput(final EventBean event, final Model model) {
        if (this.eventDao.validateCreateEvent(event, model)) {
            this.eventDao.createEvent(event, model);
            return "redirect:/admin/event";
        }
        return "admin/event";
    }
}
