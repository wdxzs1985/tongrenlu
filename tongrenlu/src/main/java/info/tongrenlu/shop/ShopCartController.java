package info.tongrenlu.shop;

import info.tongrenlu.domain.OrderBean;
import info.tongrenlu.domain.OrderItemBean;
import info.tongrenlu.domain.UserBean;
import info.tongrenlu.service.ShopOrderService;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
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
public class ShopCartController {

    @Autowired
    private ShopOrderService shopOrderService = null;

    @ModelAttribute("shoppingCart")
    public Map<String, OrderItemBean> shoppingCart() {
        return new HashMap<String, OrderItemBean>();
    }

    @ModelAttribute("newOrder")
    public OrderBean newOrder(final HttpSession session) {
        final UserBean loginUser = (UserBean) session.getAttribute("LOGIN_USER");
        final OrderBean orderBean = new OrderBean();
        orderBean.setUserBean(loginUser);
        return orderBean;
    }

    @ModelAttribute("newOrderItem")
    public OrderItemBean newOrderItem(final HttpSession session, @ModelAttribute("newOrder") final OrderBean orderBean) {
        final UserBean loginUser = (UserBean) session.getAttribute("LOGIN_USER");
        final OrderItemBean orderItemBean = new OrderItemBean();
        orderItemBean.setUserBean(loginUser);
        orderItemBean.setOrderBean(orderBean);
        return orderItemBean;
    }

    @RequestMapping(value = "/cart", method = RequestMethod.GET)
    public String doGetCart(final Model model) {

        return "shop/cart";
    }

    @RequestMapping(value = "/cart/list", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> doGetCartList(@ModelAttribute("shoppingCart") final Map<String, OrderItemBean> shoppingCart,
                                             @ModelAttribute("newOrder") final OrderBean orderBean) {
        final Map<String, Object> model = new HashMap<String, Object>();
        if (!CollectionUtils.sizeIsEmpty(shoppingCart)) {
            BigDecimal amountJp = BigDecimal.ZERO;
            BigDecimal tax = BigDecimal.ZERO;
            BigDecimal amountCn = BigDecimal.ZERO;
            BigDecimal fee = BigDecimal.ZERO;
            BigDecimal total = BigDecimal.ZERO;

            for (final OrderItemBean item : shoppingCart.values()) {
                amountJp = amountJp.add(item.getAmountJp());
                tax = tax.add(item.getTax());
                amountCn = amountCn.add(item.getAmountCn());
                fee = fee.add(item.getFee());
                total = total.add(item.getTotal());
            }

            orderBean.setAmountJp(amountJp);
            orderBean.setTax(tax);
            orderBean.setAmountCn(amountCn);
            orderBean.setFee(fee);
            orderBean.setTotal(total);
        }
        model.put("orderBean", orderBean);
        model.put("itemList", shoppingCart.values());
        return model;
    }

    @RequestMapping(value = "/cart/add", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doPostAddItem(@RequestParam final String nameOrUrl,
                                             @ModelAttribute("shoppingCart") final Map<String, OrderItemBean> shoppingCart,
                                             @ModelAttribute("newOrderItem") final OrderItemBean newOrderItem) {
        final Map<String, Object> model = new HashMap<String, Object>();

        this.shopOrderService.initWithUrl(newOrderItem, nameOrUrl);

        shoppingCart.put(newOrderItem.getTitle(), newOrderItem);

        model.put("result", true);
        return model;
    }

    @RequestMapping(value = "/cart/remove", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doPostRemoveItem(@RequestParam final String name,
                                                @ModelAttribute("shoppingCart") final Map<String, OrderItemBean> shoppingCart) {
        final Map<String, Object> model = new HashMap<String, Object>();
        shoppingCart.remove(name);

        model.put("result", true);
        return model;
    }

    @RequestMapping(value = "/order", method = RequestMethod.GET)
    public String doGetOrder(@ModelAttribute("shoppingCart") final Map<String, OrderItemBean> shoppingCart,
                             @ModelAttribute("newOrder") final OrderBean orderBean,
                             final Model model) {
        if (CollectionUtils.sizeIsEmpty(shoppingCart)) {
            return "redirect:/shop/cart";
        } else {
            model.addAttribute("itemList", shoppingCart.values());
            model.addAttribute("orderBean", orderBean);
            model.addAttribute("result", true);
            return "shop/order";
        }
    }

    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public String doPostOrder(@ModelAttribute("shoppingCart") final Map<String, OrderItemBean> shoppingCart,
                              @ModelAttribute("newOrder") final OrderBean orderBean,
                              final Model model) {
        if (CollectionUtils.sizeIsEmpty(shoppingCart)) {
            return "redirect:/shop/cart";
        } else {

            this.shopOrderService.newOrder(orderBean, shoppingCart.values());

            return "redirect:/shop/order/finish";
        }
    }

    @RequestMapping(value = "/order/finish", method = RequestMethod.GET)
    public String doGetOrder(final SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        return "shop/order_finish";
    }
}
