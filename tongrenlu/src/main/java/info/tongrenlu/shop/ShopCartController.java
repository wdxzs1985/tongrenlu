package info.tongrenlu.shop;

import info.tongrenlu.domain.OrderItemBean;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

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
@SessionAttributes("itemList")
public class ShopCartController {

    @ModelAttribute("itemList")
    public Map<String, OrderItemBean> itemList() {
        return new HashMap<String, OrderItemBean>();
    }

    @RequestMapping(value = "/cart", method = RequestMethod.GET)
    public String doGetCart(final Model model) {

        return "shop/cart";
    }

    @RequestMapping(value = "/cart/list", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> doGetCartList(@ModelAttribute("itemList") final Map<String, OrderItemBean> itemList) {
        final Map<String, Object> model = new HashMap<String, Object>();
        if (itemList != null) {
            model.put("itemList", itemList.values());
            model.put("result", true);
        } else {
            model.put("result", false);
        }
        return model;
    }

    @RequestMapping(value = "/cart/add", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doPostAddItem(@RequestParam final String nameOrUrl,
                                             @ModelAttribute("itemList") final Map<String, OrderItemBean> itemList) {
        final Map<String, Object> model = new HashMap<String, Object>();

        final OrderItemBean item = new OrderItemBean();
        item.setName(nameOrUrl);
        item.setPrice(BigDecimal.valueOf(1500));
        item.setQuantity(BigDecimal.ONE);
        item.setFee(BigDecimal.valueOf(10));
        item.setTaxRate(BigDecimal.valueOf(0.08));
        item.setExchangeRate(BigDecimal.valueOf(0.06));
        itemList.put(nameOrUrl, item);

        model.put("result", true);
        return model;
    }

    @RequestMapping(value = "/cart/remove", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doPostRemoveItem(@RequestParam final String name,
                                                @ModelAttribute("itemList") final Map<String, OrderItemBean> itemList) {
        final Map<String, Object> model = new HashMap<String, Object>();
        itemList.remove(name);

        model.put("result", true);
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
