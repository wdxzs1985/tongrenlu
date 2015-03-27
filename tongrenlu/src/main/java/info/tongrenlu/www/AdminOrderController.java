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
import java.util.Map;

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
    public String doGetIndex(@RequestParam(value = "p", defaultValue = "1") final Integer pageNumber,
                             @RequestParam(required = false) final Integer status,
                             @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                             final Model model) {

        final Map<String, Integer> dashboard = this.orderService.getDashboard(loginUser);
        model.addAllAttributes(dashboard);

        final PaginateSupport<OrderBean> page = new PaginateSupport<>(pageNumber);
        page.addParam("shopper", loginUser);

        if (status != null) {
            page.addParam("status", status);
        }
        this.orderService.searchOrder(page);
        model.addAttribute("page", page);
        model.addAttribute("status", status);
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

    @RequestMapping(method = RequestMethod.GET, value = "{orderId}/remove/{orderItemId}")
    public String doGetRemoveItem(@PathVariable final Integer orderId,
                                  @PathVariable final Integer orderItemId,
                                  @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                                  final Model model,
                                  final Locale locale) {
        final OrderBean orderBean = this.orderService.findByOrderId(orderId);
        this.throwExceptionWhenNotAllow(orderBean, loginUser, locale);
        this.orderService.removeItem(orderBean, orderItemId);
        return "redirect:/admin/order/" + orderId;
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
    public String doPostStart(@PathVariable final Integer orderId,
                              final String payLink,
                              @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                              final Model model,
                              final Locale locale) {
        final OrderBean orderBean = this.orderService.findByOrderId(orderId);
        this.throwExceptionWhenNotAllow(orderBean, loginUser, locale);
        orderBean.setPayLink(payLink);
        orderBean.setShopper(loginUser);
        orderBean.setStatus(OrderBean.STATUS_START);
        this.orderService.updateOrderStatus(orderBean, locale);
        return "redirect:/admin/order/" + orderId;
    }

    @RequestMapping(method = RequestMethod.POST, value = "{orderId}/pay")
    public String doPostPay(@PathVariable final Integer orderId,
                            @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                            final Model model,
                            final Locale locale) {
        final OrderBean orderBean = this.orderService.findByOrderId(orderId);
        this.throwExceptionWhenNotAllow(orderBean, loginUser, locale);
        orderBean.setStatus(OrderBean.STATUS_PAID);
        this.orderService.updateOrderStatus(orderBean, locale);
        return "redirect:/admin/order/" + orderId;
    }

    @RequestMapping(method = RequestMethod.POST, value = "{orderId}/send")
    public String doPostSend(@PathVariable final Integer orderId,
                             final String trackingCode,
                             @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                             final Model model,
                             final Locale locale) {
        final OrderBean orderBean = this.orderService.findByOrderId(orderId);
        this.throwExceptionWhenNotAllow(orderBean, loginUser, locale);
        orderBean.setTrackingCode(trackingCode);
        orderBean.setStatus(OrderBean.STATUS_SEND);
        this.orderService.updateOrderStatus(orderBean, locale);
        return "redirect:/admin/order/" + orderId;
    }

    @RequestMapping(method = RequestMethod.POST, value = "{orderId}/finish")
    public String doPostFinish(@PathVariable final Integer orderId,
                               @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                               final Model model,
                               final Locale locale) {
        final OrderBean orderBean = this.orderService.findByOrderId(orderId);
        this.throwExceptionWhenNotAllow(orderBean, loginUser, locale);
        orderBean.setStatus(OrderBean.STATUS_FINISH);
        this.orderService.updateOrderStatus(orderBean, locale);
        return "redirect:/admin/order/" + orderId;
    }

    @RequestMapping(method = RequestMethod.GET, value = "{orderId}/cancel")
    public String doGetCancel(@PathVariable final Integer orderId,
                              @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                              final Model model,
                              final Locale locale) {
        final OrderBean orderBean = this.orderService.findByOrderId(orderId);
        this.throwExceptionWhenNotAllow(orderBean, loginUser, locale);

        orderBean.setStatus(OrderBean.STATUS_CANCEL);
        this.orderService.updateOrderStatus(orderBean, locale);

        return "redirect:/admin/order/" + orderId;
    }

    @RequestMapping(method = RequestMethod.GET, value = "item")
    public String doGetItem(@ModelAttribute("LOGIN_USER") final UserBean loginUser, final Model model) {

        final List<OrderItemBean> itemList = this.orderService.findStockItemList();
        model.addAttribute("itemList", itemList);

        return "admin/order/item";
    }

    @RequestMapping(method = RequestMethod.POST, value = "{orderId}/item/{itemId}/pay")
    public String doPostItemPay(@PathVariable final Integer orderId,
                                @PathVariable final Integer itemId,
                                @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                                final Model model,
                                final Locale locale) {
        final OrderBean orderBean = this.orderService.findByOrderId(orderId);
        this.throwExceptionWhenNotAllow(orderBean, loginUser, locale);

        final OrderItemBean item = new OrderItemBean();
        item.setId(itemId);
        item.setStatus(OrderItemBean.STATUS_PAID);

        this.orderService.updateOrderItemStatus(item);

        return "redirect:/admin/order/item";
    }

    @RequestMapping(method = RequestMethod.POST, value = "{orderId}/item/{itemId}/receive")
    public String doPostItemReceive(@PathVariable final Integer orderId,
                                    @PathVariable final Integer itemId,
                                    @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                                    final Model model,
                                    final Locale locale) {
        final OrderBean orderBean = this.orderService.findByOrderId(orderId);
        this.throwExceptionWhenNotAllow(orderBean, loginUser, locale);

        final OrderItemBean item = new OrderItemBean();
        item.setId(itemId);
        item.setStatus(OrderItemBean.STATUS_RECEIVE);

        this.orderService.updateOrderItemStatus(item);

        return "redirect:/admin/order/item";
    }

}
