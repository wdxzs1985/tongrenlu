package info.tongrenlu.www;

import info.tongrenlu.domain.EventBean;
import info.tongrenlu.service.EventService;
import info.tongrenlu.support.ControllerSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class AdminEventController extends ControllerSupport {

    @Autowired
    private EventService eventService = null;

    @RequestMapping(method = RequestMethod.GET, value = "/admin/event")
    public String doGetIndex(@RequestParam(required = false) final Integer page,
                             final Model model) {
        return this.eventService.doGetIndex(page, model);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/admin/event/input")
    public String doPostInput(@ModelAttribute final EventBean event,
                              final Model model) {

        return this.eventService.doPostInput(event, model);
    }
}
