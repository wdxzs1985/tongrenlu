package info.tongrenlu.www;

import info.tongrenlu.domain.MusicBean;
import info.tongrenlu.domain.ReceiptItemBean;
import info.tongrenlu.service.HomeMusicService;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping("shoppingcart")
@SessionAttributes(value = { "SHOPPING_CART" })
public class ShoppingCartController {

    @Autowired
    private HomeMusicService musicService = null;

    @ModelAttribute("SHOPPING_CART")
    public Map<Integer, ReceiptItemBean> initShoppingCart() {
        return Collections.synchronizedMap(new LinkedHashMap<Integer, ReceiptItemBean>());
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(final Model model) {
        return "home/shoppingcart";
    }

    @RequestMapping(value = "list", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> doGetList(@ModelAttribute("SHOPPING_CART") final Map<Integer, ReceiptItemBean> shoppingCart) {
        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("shoppingCart", shoppingCart.values());
        return model;
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doPostAdd(@RequestParam final Integer articleId,
                                         @RequestParam(defaultValue = "1") final Integer quantity,
                                         @ModelAttribute("SHOPPING_CART") final Map<Integer, ReceiptItemBean> shoppingCart) {
        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("result", false);
        final MusicBean musicBean = this.musicService.getById(articleId);
        if (musicBean == null) {
            return model;
        }

        final ReceiptItemBean shoppingCartBean = new ReceiptItemBean();
        shoppingCartBean.setArticleBean(musicBean);
        shoppingCartBean.setQuantity(BigDecimal.valueOf(quantity));
        shoppingCart.put(articleId, shoppingCartBean);
        model.put("result", true);
        return model;
    }

    @RequestMapping(value = "change", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doPostChange(final Integer productId,
                                            @RequestParam(defaultValue = "1") final Integer quantity,
                                            @ModelAttribute("SHOPPING_CART") final Map<Integer, ReceiptItemBean> shoppingCart) {
        final Map<String, Object> model = new HashMap<String, Object>();
        final ReceiptItemBean shoppingCartBean = shoppingCart.get(productId);
        shoppingCartBean.setQuantity(BigDecimal.valueOf(quantity));
        model.put("result", true);
        return model;
    }

    @RequestMapping(value = "remove", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doPostRemove(final Integer productId,
                                            @ModelAttribute("SHOPPING_CART") final Map<Integer, ReceiptItemBean> shoppingCart) {
        final Map<String, Object> model = new HashMap<String, Object>();
        shoppingCart.remove(productId);
        model.put("result", true);
        return model;
    }

    @RequestMapping(value = "clear", method = RequestMethod.GET)
    public String doGetClear(@ModelAttribute("SHOPPING_CART") final Map<Integer, ReceiptItemBean> shoppingCart) {
        shoppingCart.clear();
        return "redirect:/shoppingcart";
    }
}
