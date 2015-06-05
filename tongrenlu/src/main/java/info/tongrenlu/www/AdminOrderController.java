package info.tongrenlu.www;

import info.tongrenlu.domain.OrderBean;
import info.tongrenlu.domain.OrderItemBean;
import info.tongrenlu.domain.OrderPayBean;
import info.tongrenlu.domain.UserBean;
import info.tongrenlu.exception.PageNotFoundException;
import info.tongrenlu.service.AdminUserService;
import info.tongrenlu.service.ConsoleOrderService;
import info.tongrenlu.support.PaginateSupport;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("LOGIN_USER")
@RequestMapping("/admin/order")
public class AdminOrderController {

    @Autowired
    private ConsoleOrderService orderService = null;
    @Autowired
    private AdminUserService adminUserService = null;
    @Autowired
    private MessageSource messageSource = null;

    protected void throwExceptionWhenNotFound(final OrderBean orderBean, final Locale locale) {
        if (orderBean == null) {
            throw new PageNotFoundException(this.messageSource.getMessage("error.pageNotFound", null, locale));
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "")
    public String doGetIndex(@RequestParam(required = false) final Integer status,
                             @RequestParam(value = "p", defaultValue = "1") final Integer pageNumber,
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

    @RequestMapping(method = RequestMethod.GET, value = "user/{userId}")
    public String doGetUserOrder(@PathVariable final Integer userId,
                                 @RequestParam(required = false) final Integer status,
                                 @RequestParam(value = "p", defaultValue = "1") final Integer pageNumber,
                                 @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                                 final Model model) {
        final UserBean userBean = this.adminUserService.getById(userId);

        final PaginateSupport<OrderBean> page = new PaginateSupport<>(pageNumber);
        page.addParam("shopper", loginUser);
        page.addParam("userBean", userBean);

        if (status != null) {
            page.addParam("status", status);
        }

        this.orderService.searchOrder(page);

        model.addAttribute("userBean", userBean);
        model.addAttribute("page", page);
        model.addAttribute("status", status);

        return "admin/order/user";
    }

    @RequestMapping(method = RequestMethod.GET, value = "user/{userId}/merge")
    public String doGetMerge(@PathVariable final Integer userId, final Locale locale) {
        final OrderBean orderBean = this.orderService.mergeOrder(userId, locale);
        if (orderBean != null) {
            return "redirect:/admin/order/" + orderBean.getId();
        } else {
            return "redirect:/admin/order";
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "{orderId}")
    public String doGetView(@PathVariable final Integer orderId,
                            @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                            final Model model,
                            final Locale locale) {
        final OrderBean orderBean = this.orderService.findByOrderId(orderId);
        this.throwExceptionWhenNotFound(orderBean, locale);
        final List<OrderItemBean> itemList = this.orderService.findItemList(orderBean);
        model.addAttribute("orderBean", orderBean);
        model.addAttribute("itemList", itemList);

        switch (orderBean.getStatus()) {
        case OrderBean.STATUS_CREATE:
            return "admin/order/view_create";

        case OrderBean.STATUS_START:
            return "admin/order/view_start";

        case OrderBean.STATUS_PAID:
            return "admin/order/view_paid";

        case OrderBean.STATUS_SEND_DIRECT:
            return "admin/order/view_send_direct";

        case OrderBean.STATUS_SEND_GROUP:
            return "admin/order/view_send_group";

        case OrderBean.STATUS_FINISH:
            return "admin/order/view_finish";

        default:
            break;
        }
        return "admin/order/view";
    }

    @RequestMapping(method = RequestMethod.GET, value = "{orderId}/remove/{orderItemId}")
    public String doGetRemoveItem(@PathVariable final Integer orderId,
                                  @PathVariable final Integer orderItemId,
                                  @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                                  final Model model,
                                  final Locale locale) {
        final OrderBean orderBean = this.orderService.findByOrderId(orderId);
        this.throwExceptionWhenNotFound(orderBean, locale);
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
                             final Integer shippingMethod,
                             final Model model,
                             final Locale locale) {
        final OrderBean orderBean = this.orderService.findByOrderId(orderId);
        this.throwExceptionWhenNotFound(orderBean, locale);

        orderBean.setShippingMethod(shippingMethod);

        final List<OrderItemBean> itemList = new ArrayList<OrderItemBean>();
        for (int i = 0; i < orderItemIdArray.length; i++) {
            final OrderItemBean orderItem = new OrderItemBean();
            orderItem.setId(orderItemIdArray.length > 0 ? orderItemIdArray[i] : null);
            orderItem.setTitle(titleArray.length > 0 ? titleArray[i] : null);
            orderItem.setUrl(urlArray.length > 0 ? urlArray[i] : null);
            orderItem.setPrice(priceArray.length > 0 ? priceArray[i] : null);
            orderItem.setQuantity(quantityArray.length > 0 ? quantityArray[i] : null);
            orderItem.setExchangeRate(exchangeRateArray.length > 0 ? exchangeRateArray[i] : null);
            orderItem.setFee(feeArray.length > 0 ? feeArray[i] : null);

            itemList.add(orderItem);
        }
        this.orderService.updateOrder(orderBean, itemList);
        return "redirect:/admin/order/" + orderId;
    }

    @RequestMapping(method = RequestMethod.POST, value = "{orderId}/start")
    public String doPostStart(@PathVariable final Integer orderId,
                              @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                              final Model model,
                              final Locale locale) {
        final OrderBean orderBean = this.orderService.findByOrderId(orderId);
        this.throwExceptionWhenNotFound(orderBean, locale);
        orderBean.setShopper(loginUser);
        orderBean.setStatus(OrderBean.STATUS_START);
        this.orderService.updateOrderStatus(orderBean, locale);

        return "redirect:/admin/order/" + orderId;
    }

    @RequestMapping(method = RequestMethod.GET, value = "{orderId}/pay")
    @ResponseBody
    public Map<String, Object> doGetPayStatus(@PathVariable final Integer orderId,
                                              @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                                              final Locale locale) {
        final OrderBean orderBean = this.orderService.findByOrderId(orderId);
        this.throwExceptionWhenNotFound(orderBean, locale);
        final Map<String, Object> model = new HashMap<String, Object>();

        final List<OrderPayBean> orderPayBeanList = this.orderService.getPayList(orderBean);
        model.put("orderPayBeanList", orderPayBeanList);
        model.put("result", true);
        return model;
    }

    @RequestMapping(method = RequestMethod.POST, value = "{orderId}/pay/add")
    @ResponseBody
    public Map<String, Object> doPostPayAdd(@PathVariable final Integer orderId,
                                            final String title,
                                            final String payLink,
                                            final BigDecimal amount,
                                            @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                                            final Locale locale) {
        final OrderBean orderBean = this.orderService.findByOrderId(orderId);
        this.throwExceptionWhenNotFound(orderBean, locale);
        final Map<String, Object> model = new HashMap<String, Object>();

        final OrderPayBean orderPayBean = new OrderPayBean();
        orderPayBean.setOrderBean(orderBean);
        orderPayBean.setUserBean(orderBean.getUserBean());
        orderPayBean.setTitle(title);
        orderPayBean.setPayLink(payLink);
        orderPayBean.setAmount(amount);
        orderPayBean.setStatus(OrderPayBean.STATUS_CREATE);
        this.orderService.addPay(orderPayBean, locale);

        model.put("result", true);

        return model;
    }

    @RequestMapping(method = RequestMethod.POST, value = "{orderId}/pay/update")
    @ResponseBody
    public Map<String, Object> doPostPayUpdate(@PathVariable final Integer orderId,
                                               final Integer orderPayId,
                                               final String payNo,
                                               @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                                               final Locale locale) {
        final OrderBean orderBean = this.orderService.findByOrderId(orderId);
        this.throwExceptionWhenNotFound(orderBean, locale);
        final Map<String, Object> model = new HashMap<String, Object>();

        model.put("orderPayId", orderPayId);
        model.put("status", OrderPayBean.STATUS_PAID);
        model.put("payNo", payNo);

        this.orderService.updatePayStatus(model);

        model.put("result", true);

        return model;
    }

    @RequestMapping(method = RequestMethod.POST, value = "{orderId}/pay/remove")
    @ResponseBody
    public Map<String, Object> doPostPayRemove(@PathVariable final Integer orderId,
                                               final Integer orderPayId,
                                               @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                                               final Locale locale) {
        final OrderBean orderBean = this.orderService.findByOrderId(orderId);
        this.throwExceptionWhenNotFound(orderBean, locale);
        final Map<String, Object> model = new HashMap<String, Object>();

        model.put("orderPayId", orderPayId);

        this.orderService.removePay(model);

        model.put("result", true);

        return model;
    }

    @RequestMapping(method = RequestMethod.POST, value = "{orderId}/send/group")
    public String doPostSendGroup(@PathVariable final Integer orderId,
                                  @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                                  final Model model,
                                  final Locale locale) {
        final OrderBean orderBean = this.orderService.findByOrderId(orderId);
        this.throwExceptionWhenNotFound(orderBean, locale);

        orderBean.setStatus(OrderBean.STATUS_SEND_GROUP);
        this.orderService.updateOrderStatus(orderBean, locale);
        return "redirect:/admin/order/" + orderId;
    }

    @RequestMapping(method = RequestMethod.POST, value = "{orderId}/send/direct")
    public String doPostSendDirect(@PathVariable final Integer orderId,
                                   final String trackingCode,
                                   @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                                   final Model model,
                                   final Locale locale) {
        final OrderBean orderBean = this.orderService.findByOrderId(orderId);
        this.throwExceptionWhenNotFound(orderBean, locale);

        orderBean.setTrackingCode(trackingCode);
        orderBean.setStatus(OrderBean.STATUS_SEND_DIRECT);
        this.orderService.updateOrderStatus(orderBean, locale);
        return "redirect:/admin/order/" + orderId;
    }

    @RequestMapping(method = RequestMethod.POST, value = "{orderId}/finish")
    public String doPostFinish(@PathVariable final Integer orderId,
                               @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                               final Model model,
                               final Locale locale) {
        final OrderBean orderBean = this.orderService.findByOrderId(orderId);
        this.throwExceptionWhenNotFound(orderBean, locale);

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
        this.throwExceptionWhenNotFound(orderBean, locale);

        orderBean.setStatus(OrderBean.STATUS_CANCEL);
        this.orderService.updateOrderStatus(orderBean, locale);

        return "redirect:/admin/order/" + orderId;
    }

    @RequestMapping(method = RequestMethod.GET, value = "{orderId}/restore")
    public String doGetRestore(@PathVariable final Integer orderId,
                               @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                               final Model model,
                               final Locale locale) {
        final OrderBean orderBean = this.orderService.findByOrderId(orderId);
        this.throwExceptionWhenNotFound(orderBean, locale);

        orderBean.setStatus(OrderBean.STATUS_CREATE);
        this.orderService.updateOrderStatus(orderBean, locale);

        return "redirect:/admin/order/" + orderId;
    }

    @RequestMapping(method = RequestMethod.GET, value = "items")
    public String doGetItems(@ModelAttribute("LOGIN_USER") final UserBean loginUser,
                             final Model model,
                             final Locale locale) {
        final List<OrderItemBean> stockItemList = this.orderService.findStockItemList(loginUser);
        final Map<String, Object> stockItemMap = new HashMap<String, Object>();
        for (final OrderItemBean item : stockItemList) {
            final String title = item.getTitle();
            Map<String, Object> itemMap = (Map<String, Object>) stockItemMap.get(title);
            if (itemMap == null) {
                itemMap = new HashMap<String, Object>();
                stockItemMap.put(title, itemMap);
            }

            BigDecimal quantity = (BigDecimal) itemMap.get("quantity");
            quantity = quantity == null ? BigDecimal.ZERO : quantity;
            quantity = quantity.add(item.getQuantity());
            itemMap.put("quantity", quantity);

            List<Integer> itemIdList = (List<Integer>) itemMap.get("itemIdList");
            itemIdList = itemIdList == null ? new ArrayList<Integer>() : itemIdList;
            itemIdList.add(item.getId());
            itemMap.put("itemIdList", itemIdList);

            List<OrderItemBean> itemList = (List<OrderItemBean>) itemMap.get("itemList");
            itemList = itemList == null ? new ArrayList<OrderItemBean>() : itemList;
            itemList.add(item);
            itemMap.put("itemList", itemList);
        }
        model.addAttribute("stockItemMap", stockItemMap);
        return "admin/order/items";
    }

    @RequestMapping(method = RequestMethod.POST, value = "item/status")
    @ResponseBody
    public Map<String, Object> doPostItemPay(@RequestParam("itemId[]") final Integer[] itemIds,
                                             final Integer status,
                                             @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                                             final Locale locale) {
        final Map<String, Object> model = new HashMap<>();

        final List<OrderItemBean> items = new ArrayList<>();
        for (final Integer itemId : itemIds) {
            final OrderItemBean item = new OrderItemBean();
            item.setId(itemId);
            item.setStatus(status);

            items.add(item);
        }

        this.orderService.updateOrderItemStatus(items);

        model.put("result", true);
        model.put("status", status);
        return model;
    }

}
