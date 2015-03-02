package info.tongrenlu.shop;

import info.tongrenlu.domain.OrderItemBean;
import info.tongrenlu.domain.UserBean;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping("/shop")
@SessionAttributes("shoppingCart")
public class ShopCartController {

    @ModelAttribute("shoppingCart")
    public Map<String, OrderItemBean> shoppingCart() {
        return new HashMap<String, OrderItemBean>();
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
    public Map<String, Object> doGetCartList(@ModelAttribute("shoppingCart") final Map<String, OrderItemBean> shoppingCart) {
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

            model.put("amountJp", amountJp);
            model.put("tax", tax);
            model.put("amountCn", amountCn);
            model.put("fee", fee);
            model.put("total", total);
        }
        model.put("itemList", shoppingCart.values());
        return model;
    }

    @RequestMapping(value = "/cart/add", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doPostAddItem(@RequestParam final String nameOrUrl,
                                             @ModelAttribute("shoppingCart") final Map<String, OrderItemBean> shoppingCart) {
        final Map<String, Object> model = new HashMap<String, Object>();

        final OrderItemBean item = new OrderItemBean();
        item.setName("[Sound CYCLONE] Sparkle!");
        item.setShop("toranoana");
        item.setUrl("http://www.toranoana.jp/mailorder/article/04/0030/04/65/040030046515.html");
        item.setPrice(BigDecimal.valueOf(1500));
        item.setQuantity(BigDecimal.ONE);
        item.setFee(BigDecimal.valueOf(10));
        item.setTaxRate(BigDecimal.valueOf(0.08));
        item.setExchangeRate(BigDecimal.valueOf(0.06));
        shoppingCart.put(nameOrUrl, item);

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
                             final Model model) {
        if (CollectionUtils.sizeIsEmpty(shoppingCart)) {
            return "redirect:/shop/cart";
        } else {
            model.addAttribute("itemList", shoppingCart.values());

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

            model.addAttribute("amountJp", amountJp);
            model.addAttribute("tax", tax);
            model.addAttribute("amountCn", amountCn);
            model.addAttribute("fee", fee);
            model.addAttribute("total", total);
            model.addAttribute("result", true);
            return "shop/order";
        }
    }

    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public String doPostOrder(@ModelAttribute("shoppingCart") final Map<String, OrderItemBean> shoppingCart,
                              @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                              final Model model) {
        if (CollectionUtils.sizeIsEmpty(shoppingCart)) {
            return "redirect:/shop/cart";
        } else {

            return "shop/order";
        }
    }
}
