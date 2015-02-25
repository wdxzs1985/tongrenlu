package info.tongrenlu.shop;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/shop")
public class ShopCartController {

    @RequestMapping(value = "/cart", method = RequestMethod.GET)
    public String doGetCart(final Model model) {

        return "shop/cart";
    }

    @RequestMapping(value = "/cart/list", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> doGetCartList() {
        final Map<String, Object> model = new HashMap<String, Object>();

        return model;
    }

    @RequestMapping(value = "/cart/add", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doGetCart(final String name, final String url) {
        final Map<String, Object> model = new HashMap<String, Object>();

        return model;
    }

    @RequestMapping(value = "/order", method = RequestMethod.GET)
    public String doGetOrder(final Model model) {

        return "shop/order_index";
    }

    @RequestMapping(value = "/order/input", method = RequestMethod.GET)
    public String doGetOrderInput(final Model model) {

        return "shop/order_input";
    }

    @RequestMapping(value = "/order/{orderId}", method = RequestMethod.GET)
    public String doGetOrderView(final Integer orderId, final Model model) {

        return "shop/order_view";
    }

}
