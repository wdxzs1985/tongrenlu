package info.tongrenlu.service;

import info.tongrenlu.domain.OrderBean;
import info.tongrenlu.domain.OrderItemBean;
import info.tongrenlu.manager.OrderManager;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ShopOrderService {

    public static final Pattern PATTERN_TORANOANA = Pattern.compile("http://www.toranoana.jp/mailorder/.*");

    @Autowired
    private OrderManager orderManager = null;

    public void initWithUrl(final OrderItemBean item, final String nameOrUrl) {
        if (PATTERN_TORANOANA.matcher(nameOrUrl).find()) {
            item.setTitle("[Sound CYCLONE] Sparkle!");
            item.setShop("toranoana");
            item.setUrl(nameOrUrl);
        } else {
            item.setTitle(nameOrUrl);
        }

        item.setPrice(BigDecimal.valueOf(1500));
        item.setQuantity(BigDecimal.ONE);
        item.setFee(BigDecimal.valueOf(10));
        item.setTaxRate(BigDecimal.valueOf(0.08));
        item.setExchangeRate(BigDecimal.valueOf(0.06));
    }

    public boolean newOrder(final OrderBean orderBean, final Collection<OrderItemBean> itemList) {
        this.orderManager.insertOrder(orderBean);
        this.orderManager.insertOrderItems(itemList);
        return true;
    }
}
