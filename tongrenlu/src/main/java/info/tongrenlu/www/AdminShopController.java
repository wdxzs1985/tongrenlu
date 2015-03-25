package info.tongrenlu.www;

import info.tongrenlu.domain.ShopBean;
import info.tongrenlu.service.AdminShopService;

import java.math.BigDecimal;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("LOGIN_USER")
@RequestMapping("/admin/shop")
public class AdminShopController {

    @Autowired
    private AdminShopService adminShopService = null;

    @RequestMapping(method = RequestMethod.GET, value = "")
    public String doGetView(final Model model, final Locale locale) {
        final ShopBean shopBean = this.adminShopService.getDefaultShop();
        model.addAttribute("shopBean", shopBean);
        return "admin/shop/view";
    }

    @RequestMapping(method = RequestMethod.POST, value = "")
    public String doPostView(final BigDecimal taxRate,
                             final BigDecimal exchangeRate,
                             final BigDecimal feeMailorder,
                             final BigDecimal feeEvent,
                             final Model model,
                             final Locale locale) {
        final ShopBean shopBean = new ShopBean();
        shopBean.setTaxRate(taxRate);
        shopBean.setExchangeRate(exchangeRate);
        shopBean.setFeeMailorder(feeMailorder);
        shopBean.setFeeEvent(feeEvent);

        this.adminShopService.updateDefaultShop(shopBean);
        return "redirect:/admin/shop";
    }
}
