package info.tongrenlu.www;

import info.tongrenlu.domain.OrderBean;
import info.tongrenlu.domain.OrderItemBean;
import info.tongrenlu.domain.UserBean;
import info.tongrenlu.exception.ForbiddenException;
import info.tongrenlu.exception.PageNotFoundException;
import info.tongrenlu.service.ConsoleOrderService;
import info.tongrenlu.support.PaginateSupport;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("LOGIN_USER")
@RequestMapping("/admin/order")
public class AdminOrderController {

    @Autowired
    private ConsoleOrderService orderService = null;
    @Autowired
    private MessageSource messageSource = null;

    protected void throwExceptionWhenNotAllow(final OrderBean orderBean, final UserBean loginUser, final Locale locale) {
        if (orderBean == null) {
            throw new PageNotFoundException(this.messageSource.getMessage("error.pageNotFound", null, locale));
        } else if (!loginUser.isShopAdmin()) {
            throw new ForbiddenException(this.messageSource.getMessage("error.forbidden", null, locale));
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "")
    public String doGetIndex(@RequestParam(value = "p", defaultValue = "1") final Integer pageNumber, final Model model) {
        final PaginateSupport<OrderBean> page = new PaginateSupport<>(pageNumber);
        this.orderService.searchOrder(page);
        model.addAttribute("page", page);

        return "admin/order/index";
    }

    @RequestMapping(method = RequestMethod.GET, value = "{orderId}")
    public String doGetView(@PathVariable final Integer orderId,
                            @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                            final Model model,
                            final Locale locale) {
        final OrderBean orderBean = this.orderService.findByOrderId(orderId);
        this.throwExceptionWhenNotAllow(orderBean, loginUser, locale);
        final List<OrderItemBean> itemList = this.orderService.findItemList(orderBean);

        model.addAttribute("orderBean", orderBean);
        model.addAttribute("itemList", itemList);

        return "admin/order/view";
    }

    @RequestMapping(method = RequestMethod.POST, value = "{orderId}")
    public String doPostEdit(@PathVariable final Integer orderId,
                             @RequestParam("orderItemId[]") final Integer[] orderItemIdArray,
                             @RequestParam("title[]") final String[] titleArray,
                             @RequestParam("url[]") final String[] urlArray,
                             @RequestParam("price[]") final BigDecimal[] priceArray,
                             @RequestParam("quantity[]") final BigDecimal[] quantityArray,
                             @RequestParam("exchangeRate[]") final BigDecimal[] exchangeRateArray,
                             @RequestParam("fee[]") final BigDecimal[] feeArray,
                             @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                             final Model model,
                             final Locale locale) {
        final OrderBean orderBean = this.orderService.findByOrderId(orderId);
        this.throwExceptionWhenNotAllow(orderBean, loginUser, locale);

        final List<OrderItemBean> itemList = new ArrayList<OrderItemBean>();

        for (int i = 0; i < orderItemIdArray.length; i++) {
            final OrderItemBean orderItem = new OrderItemBean();
            orderItem.setId(orderItemIdArray[i]);
            orderItem.setTitle(titleArray[i]);
            orderItem.setUrl(urlArray[i]);
            orderItem.setPrice(priceArray[i]);
            orderItem.setQuantity(quantityArray[i]);
            orderItem.setExchangeRate(exchangeRateArray[i]);
            orderItem.setFee(feeArray[i]);

            itemList.add(orderItem);
        }

        this.orderService.updateOrder(orderBean, itemList);
        return "redirect:/admin/order/" + orderId;
    }

    @RequestMapping(method = RequestMethod.POST, value = "{orderId}/start")
    public String doGetStart(@PathVariable final Integer orderId,
                             @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                             final Model model,
                             final Locale locale) {
        final OrderBean orderBean = this.orderService.findByOrderId(orderId);
        this.throwExceptionWhenNotAllow(orderBean, loginUser, locale);

        this.orderService.startOrder(orderBean);

        return "redirect:/admin/order/" + orderId;
    }

    @RequestMapping(method = RequestMethod.POST, value = "{orderId}/finish")
    public String doGetFinish(@PathVariable final Integer orderId,
                              @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                              final Model model,
                              final Locale locale) {
        final OrderBean orderBean = this.orderService.findByOrderId(orderId);
        this.throwExceptionWhenNotAllow(orderBean, loginUser, locale);

        this.orderService.finishOrder(orderBean);

        return "redirect:/admin/order/" + orderId;
    }

    @RequestMapping(method = RequestMethod.POST, value = "{orderId}/cancel")
    public String doGetCancel(@PathVariable final Integer orderId,
                              @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                              final Model model,
                              final Locale locale) {
        final OrderBean orderBean = this.orderService.findByOrderId(orderId);
        this.throwExceptionWhenNotAllow(orderBean, loginUser, locale);

        this.orderService.cancelOrder(orderBean);

        return "redirect:/admin/order/" + orderId;
    }
}
