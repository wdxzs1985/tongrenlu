package info.tongrenlu.mail;

import info.tongrenlu.Application;
import info.tongrenlu.domain.OrderBean;
import info.tongrenlu.domain.OrderItemBean;
import info.tongrenlu.domain.UserBean;
import info.tongrenlu.service.ConsoleOrderService;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class MailResolvorTest {

    @Autowired
    private MailResolvor mailResolvor;
    @Autowired
    private ConsoleOrderService orderService;
    @Autowired
    private MessageSource messageSource;

    @Test
    public void test() {
        final OrderBean orderBean = this.orderService.findByOrderId(5);
        final Collection<OrderItemBean> itemList = this.orderService.findItemList(orderBean);
        final UserBean userBean = orderBean.getUserBean();

        final MailModel mailModel = this.mailResolvor.createMailModel();
        mailModel.setTo(this.mailResolvor.createAddress(userBean.getEmail(), userBean.getNickname()));
        mailModel.setSubject(this.messageSource.getMessage("mail.newOrder", null, null));
        mailModel.setTemplate("new_order");

        mailModel.addAttribute("userBean", userBean);
        mailModel.addAttribute("orderBean", orderBean);
        mailModel.addAttribute("itemList", itemList);

        this.mailResolvor.send(mailModel);
    }

}
