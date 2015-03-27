package info.tongrenlu.mail;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

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

    @Value("${spring.mail.defaultEncoding:utf-8}")
    private String defaultEncoding = null;

    @Value("${spring.mail.from}")
    private String from = null;

    @Value("${spring.mail.prefix:mail}")
    private String prefix = null;

    @Value("${spring.mail.suffix:vm}")
    private String suffix = null;

    @Value("${spring.mail.debug:true}")
    private boolean debug = true;

    public MailModel createMailModel() {
        final MailModel model = new MailModel();
        return model;
    }

    public InternetAddress createAddress(final String address, final String personal) {
        try {
            return new InternetAddress(address, personal, this.defaultEncoding);
        } catch (final UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public void send(final MailModel model) {
        final MimeMessage message = this.mailSender.createMimeMessage();
        final MimeMessageHelper messageHelper = new MimeMessageHelper(message);

        final String templateLocation = String.format("%s/%s.%s", this.prefix, model.getTemplate(), this.suffix);
        final Map<String, Object> modelMap = model.asMap();
        final String text = VelocityEngineUtils.mergeTemplateIntoString(this.velocityEngine,
                                                                        templateLocation,
                                                                        this.defaultEncoding,
                                                                        modelMap);

        try {
            messageHelper.setSubject(model.getSubject());
            messageHelper.setFrom(this.from);
            messageHelper.setTo(model.getTo());
            if (model.getBcc() != null) {
                messageHelper.setBcc(model.getBcc());
            } else {
                messageHelper.setBcc(this.from);
            }
            messageHelper.setText(text);

            if (this.debug) {
                this.log.debug(text);
            } else {
                this.mailSender.send(message);
            }
        } catch (final MailException e) {
            this.log.error(e.getMessage(), e);
        } catch (final MessagingException e) {
            this.log.error(e.getMessage(), e);
        }
    }
}
