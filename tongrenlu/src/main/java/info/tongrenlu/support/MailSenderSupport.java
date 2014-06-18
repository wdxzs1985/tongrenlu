package info.tongrenlu.support;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class MailSenderSupport {

    @Autowired
    private MailSender mailSender = null;

    @Autowired
    private SimpleMailMessage templateMessage = null;

    private String templatePath = null;

    private String suffix = null;

    public String getText(final String template, final Map<String, Object> model) {
        // final String text =
        // VelocityEngineUtils.mergeTemplateIntoString(this.velocityEngine,
        // this.getTemplatePath() + template
        // + this.getSuffix(),
        // "utf-8",
        // model);
        // return text;
        return "text";
    }

    public void send(final String subject, final String to, final String text) {
        final SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);

        msg.setSubject(subject);
        msg.setTo(to);
        msg.setText(text);

        this.mailSender.send(msg);
    }

    public String getTemplatePath() {
        return this.templatePath;
    }

    public void setTemplatePath(final String templatePath) {
        this.templatePath = templatePath;
    }

    public String getSuffix() {
        return this.suffix;
    }

    public void setSuffix(final String suffix) {
        this.suffix = suffix;
    }
}
