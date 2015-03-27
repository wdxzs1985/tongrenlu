package info.tongrenlu.mail;

import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

@Component
public class MailResolvor {

    private final Log log = LogFactory.getLog(this.getClass());

    @Autowired
    private VelocityEngine velocityEngine;

    @Autowired
    private JavaMailSender mailSender = null;

    @Value("${spring.mail.from}")
    private String from = null;

    @Value("${spring.mail.encoding:utf-8}")
    private String encoding = null;

    @Value("${spring.mail.prefix:mail}")
    private String prefix = null;

    @Value("${spring.mail.suffix:vm}")
    private String suffix = null;

    public MailModel createMailModel() {
        final MailModel model = new MailModel();
        return model;
    }

    public void send(final MailModel model) {
        final String templateLocation = String.format("%s/%s.%s", this.prefix, model.getTemplate(), this.suffix);
        final Map<String, Object> modelMap = model.asMap();
        final String text = VelocityEngineUtils.mergeTemplateIntoString(this.velocityEngine,
                                                                        templateLocation,
                                                                        this.encoding,
                                                                        modelMap);

        try {
            final MimeMessage message = this.mailSender.createMimeMessage();
            final MimeMessageHelper messageHelper = new MimeMessageHelper(message);

            messageHelper.setSubject(model.getSubject());
            messageHelper.setFrom(this.from);
            messageHelper.setTo(model.getTo());
            if (StringUtils.isNotBlank(model.getBcc())) {
                messageHelper.setBcc(model.getBcc());
            } else {
                messageHelper.setBcc(this.from);
            }
            messageHelper.setText(text);

            this.mailSender.send(message);
        } catch (final MailException e) {
            this.log.error(e.getMessage(), e);
        } catch (final MessagingException e) {
            this.log.error(e.getMessage(), e);
        }
    }
}
