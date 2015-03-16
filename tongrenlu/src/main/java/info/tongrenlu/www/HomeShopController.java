package info.tongrenlu.www;

import info.tongrenlu.domain.OrderBean;
import info.tongrenlu.domain.OrderItemBean;
import info.tongrenlu.domain.UserBean;
import info.tongrenlu.service.ShopOrderService;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequestMapping("/shop")
@SessionAttributes({ "shoppingCart", "newOrder" })
public class HomeShopController {

    @Autowired
    private ShopOrderService shopOrderService = null;

    private Log log = LogFactory.getLog(this.getClass());

    @ModelAttribute("shoppingCart")
    public Map<String, OrderItemBean> shoppingCart() {
        return new LinkedHashMap<String, OrderItemBean>();
    }

    @ModelAttribute("LOGIN_USER")
    public UserBean initLoginUser(final HttpSession session) {
        return (UserBean) session.getAttribute("LOGIN_USER");
    }

    @RequestMapping(value = "/cart", method = RequestMethod.GET)
    public String doGetCart(final Model model) {

        return "shop/cart";
    }

    @RequestMapping(value = "/cart/list", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> doGetCartList(@ModelAttribute("shoppingCart") final Map<String, OrderItemBean> shoppingCart,
                                             @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                                             final Locale locale) {
        final Map<String, Object> model = new HashMap<String, Object>();
        final OrderBean orderBean = this.shopOrderService.makeOrderBean(loginUser);
        final List<OrderItemBean> itemList = this.shopOrderService.makeItemList(shoppingCart, orderBean, locale);

        model.put("orderBean", orderBean);
        model.put("itemList", itemList);

        return model;
    }

    @RequestMapping(value = "/cart/add", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doPostAddItem(@RequestParam final String titleOrUrl,
                                             @ModelAttribute("shoppingCart") final Map<String, OrderItemBean> shoppingCart) {
        final Map<String, Object> model = new HashMap<String, Object>();

        if (StringUtils.isNotBlank(titleOrUrl)) {
            final OrderItemBean item = this.shopOrderService.initWithUrl(titleOrUrl);
            shoppingCart.put(item.getTitle(), item);
            model.put("result", true);
        } else {
            model.put("result", false);
        }

        return model;
    }

    @RequestMapping(value = "/cart/update", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doPostUpdateItem(@RequestParam final String title,
                                                @RequestParam final Integer quantity,
                                                @ModelAttribute("shoppingCart") final Map<String, OrderItemBean> shoppingCart) {
        final Map<String, Object> model = new HashMap<String, Object>();

        final OrderItemBean orderItemBean = shoppingCart.get(title);
        if (orderItemBean != null) {
            orderItemBean.setQuantity(BigDecimal.valueOf(quantity));
        }

        model.put("result", true);
        return model;
    }

    @RequestMapping(value = "/cart/remove", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doPostRemoveItem(@RequestParam final String title,
                                                @ModelAttribute("shoppingCart") final Map<String, OrderItemBean> shoppingCart) {
        final Map<String, Object> model = new HashMap<String, Object>();
        shoppingCart.remove(title);

        model.put("result", true);
        return model;
    }

    @RequestMapping(value = "/cart/clear", method = RequestMethod.GET)
    public String doGetCartClear(final SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        return "redirect:/shop/cart";
    }

    @RequestMapping(value = "/order", method = RequestMethod.GET)
    public String doGetOrder(@ModelAttribute("shoppingCart") final Map<String, OrderItemBean> shoppingCart,
                             @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                             final Model model,
                             final Locale locale) {
        if (CollectionUtils.sizeIsEmpty(shoppingCart)) {
            return "redirect:/shop/cart";
        } else {
            final OrderBean orderBean = this.shopOrderService.makeOrderBean(loginUser);
            final List<OrderItemBean> itemList = this.shopOrderService.makeItemList(shoppingCart, orderBean, locale);
            model.addAttribute("orderBean", orderBean);
            model.addAttribute("itemList", itemList);
            model.addAttribute("result", true);

            return "shop/order";
        }
    }

    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public String doPostOrder(@ModelAttribute("shoppingCart") final Map<String, OrderItemBean> shoppingCart,
                              @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                              final Model model,
                              final Locale locale) {
        if (CollectionUtils.sizeIsEmpty(shoppingCart)) {
            return "redirect:/shop/cart";
        } else {

            final OrderBean orderBean = this.shopOrderService.makeOrderBean(loginUser);
            final List<OrderItemBean> itemList = this.shopOrderService.makeItemList(shoppingCart, orderBean, locale);
            model.addAttribute("orderBean", orderBean);
            model.addAttribute("itemList", itemList);

            this.shopOrderService.newOrder(orderBean, itemList, locale);

            return "redirect:/shop/order/finish";
        }
    }

    @RequestMapping(value = "/order/finish", method = RequestMethod.GET)
    public String doGetOrder(final SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        return "shop/order_finish";
    }
}