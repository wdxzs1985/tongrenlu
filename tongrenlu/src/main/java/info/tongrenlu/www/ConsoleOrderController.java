package info.tongrenlu.www;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import info.tongrenlu.domain.OrderBean;
import info.tongrenlu.domain.OrderItemBean;
import info.tongrenlu.domain.OrderPayBean;
import info.tongrenlu.domain.UserBean;
import info.tongrenlu.exception.ForbiddenException;
import info.tongrenlu.exception.PageNotFoundException;
import info.tongrenlu.service.ConsoleOrderService;
import info.tongrenlu.support.PaginateSupport;

@SessionAttributes("LOGIN_USER")
@RequestMapping("/console/order")
public class ConsoleOrderController {

    @Autowired
    private ConsoleOrderService orderService = null;
    @Autowired
    private MessageSource messageSource = null;

    protected void throwExceptionWhenNotAllow(final OrderBean orderBean,
                                              final UserBean loginUser,
                                              final Locale locale) {
        if (orderBean == null) {
            throw new PageNotFoundException(this.messageSource.getMessage("error.pageNotFound",
                                                                          null,
                                                                          locale));
        } else if (!loginUser.equals(orderBean.getUserBean()) && !loginUser.isShopAdmin()) {
            throw new ForbiddenException(this.messageSource.getMessage("error.forbidden",
                                                                       null,
                                                                       locale));
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "")
    public String doGetIndex(@RequestParam(value = "p", defaultValue = "1") final Integer pageNumber,
                             @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                             final Model model) {
        final PaginateSupport<OrderBean> page = new PaginateSupport<>(pageNumber);
        page.addParam("userBean", loginUser);
        this.orderService.searchOrder(page);
        model.addAttribute("page", page);

        return "console/order/index";
    }

    @RequestMapping(method = RequestMethod.GET, value = "{orderId}")
    public String doGetView(@PathVariable final Integer orderId,
                            @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                            final Model model,
                            final Locale locale) {
        final OrderBean orderBean = this.orderService.findByOrderId(orderId);
        this.throwExceptionWhenNotAllow(orderBean, loginUser, locale);
        final List<OrderItemBean> itemList = this.orderService.findItemList(orderBean);
        final List<OrderPayBean> payList = this.orderService.findPayList(orderBean);

        model.addAttribute("orderBean", orderBean);
        model.addAttribute("itemList", itemList);
        model.addAttribute("payList", payList);

        return "console/order/view";
    }

}
