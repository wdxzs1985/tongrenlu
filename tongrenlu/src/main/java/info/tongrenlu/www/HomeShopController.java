package info.tongrenlu.www;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import info.tongrenlu.domain.OrderBean;
import info.tongrenlu.domain.OrderItemBean;
import info.tongrenlu.domain.UserBean;
import info.tongrenlu.service.ShopOrderService;

@RequestMapping("/shop")
@SessionAttributes({ "shoppingCart", "newOrder" })
public class HomeShopController {

    private ShopOrderService shopOrderService = null;
    private Log log = LogFactory.getLog(this.getClass());

    @ModelAttribute("shoppingCart")
    public Map<String, OrderItemBean> shoppingCart() {
        return new LinkedHashMap<String, OrderItemBean>();
    }

    @ModelAttribute("LOGIN_USER")
    public UserBean initLoginUser(
                                  final HttpSession session) {
        return (UserBean) session.getAttribute("LOGIN_USER");
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String doGetMailOrder(
                                 final Model model) {
        return "shop/index";
    }

    @RequestMapping(value = "/mailorder", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doPostAddMailOrder(
                                                  @RequestParam final String url,
                                                  @ModelAttribute("shoppingCart") final Map<String, OrderItemBean> shoppingCart,
                                                  final Locale locale) {
        final Map<String, Object> model = new HashMap<String, Object>();

        final OrderItemBean item = this.shopOrderService.initWithUrl(url,
                                                                     model,
                                                                     locale);
        if (item != null) {
            shoppingCart.put(item.getTitle(), item);
            model.put("result", true);
        } else {
            model.put("result", false);
        }
        return model;
    }

    @RequestMapping(value = "/event", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doPostEvent(
                                           @RequestParam(defaultValue = "", required = false) final String title,
                                           @RequestParam(defaultValue = "0", required = false) final BigDecimal price,
                                           @RequestParam(defaultValue = "", required = false) final String url,
                                           @ModelAttribute("shoppingCart") final Map<String, OrderItemBean> shoppingCart,
                                           final Locale locale) {
        final Map<String, Object> model = new HashMap<String, Object>();
        final OrderItemBean item = this.shopOrderService.initEventItem(title,
                                                                       price,
                                                                       url,
                                                                       model,
                                                                       locale);
        if (item != null) {
            shoppingCart.put(item.getTitle(), item);
            model.put("result", true);
        } else {
            model.put("result", false);
        }

        return model;
    }

    @RequestMapping(value = "/cart/list", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> doGetCartList(
                                             @ModelAttribute("shoppingCart") final Map<String, OrderItemBean> shoppingCart,
                                             @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                                             final Locale locale) {
        final Map<String, Object> model = new HashMap<String, Object>();
        final OrderBean orderBean = this.shopOrderService.makeOrderBean(loginUser);
        final List<OrderItemBean> itemList = this.shopOrderService.makeItemList(shoppingCart,
                                                                                orderBean);

        model.put("orderBean", orderBean);
        model.put("itemList", itemList);

        model.put("emsPrice",
                  this.shopOrderService.getEmsPrice(orderBean.getQuantity()));
        model.put("salPrice",
                  this.shopOrderService.getSalPrice(orderBean.getQuantity()));
        model.put("groupPrice",
                  this.shopOrderService.getGroupPrice(orderBean.getQuantity()));

        return model;
    }

    @RequestMapping(value = "/cart/update", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doPostUpdateItem(
                                                @RequestParam final String title,
                                                @RequestParam final Integer quantity,
                                                @ModelAttribute("shoppingCart") final Map<String, OrderItemBean> shoppingCart) {
        final Map<String, Object> model = new HashMap<String, Object>();

        final OrderItemBean orderItemBean = shoppingCart.get(title);
        if (orderItemBean != null) {
            orderItemBean.setQuantity(BigDecimal.valueOf(quantity));
            if (BigDecimal.ZERO.compareTo(orderItemBean.getQuantity()) >= 0) {
                shoppingCart.remove(title);
            }
        }
        model.put("result", true);
        return model;
    }

    @RequestMapping(value = "/cart/remove", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doPostRemoveItem(
                                                @RequestParam final String title,
                                                @ModelAttribute("shoppingCart") final Map<String, OrderItemBean> shoppingCart) {
        final Map<String, Object> model = new HashMap<String, Object>();
        shoppingCart.remove(title);

        model.put("result", true);
        return model;
    }

    @RequestMapping(value = "/cart/clear", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doGetCartClear(
                                              final SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        final Map<String, Object> model = new HashMap<String, Object>();

        model.put("result", true);
        return model;
    }

    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public String doPostOrder(
                              @ModelAttribute("shoppingCart") final Map<String, OrderItemBean> shoppingCart,
                              @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                              final Integer shippingMethod, final Model model,
                              final Locale locale) {
        if (CollectionUtils.sizeIsEmpty(shoppingCart)) {
            return "redirect:/shop";
        } else {
            final OrderBean orderBean = this.shopOrderService.makeOrderBean(loginUser);
            orderBean.setShippingMethod(shippingMethod);
            final List<OrderItemBean> itemList = this.shopOrderService.makeItemList(shoppingCart,
                                                                                    orderBean);
            this.shopOrderService.newOrder(orderBean, itemList, locale);

            return "redirect:/shop/order/finish";
        }
    }

    @RequestMapping(value = "/order/finish", method = RequestMethod.GET)
    public String doGetOrder(
                             final SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        return "shop/order_finish";
    }
}
