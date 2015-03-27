package info.tongrenlu.mail;

import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.InternetAddress;

public class MailModel {

    private String subject;

    private InternetAddress to;

    private InternetAddress bcc;

    private String template;

    private Map<String, Object> modelMap = new HashMap<String, Object>();

    public String getTemplate() {
        return this.template;
    }

    public void setTemplate(final String template) {
        this.template = template;
    }

    public void addAttribute(final String attribute, final Object value) {
        if (value != null) {
            this.modelMap.put(attribute, value);
        }
    }

    public Map<String, Object> asMap() {
        return this.modelMap;
    }

    public String getSubject() {
        return this.subject;
    }

    public void setSubject(final String subject) {
        this.subject = subject;
    }

    public InternetAddress getTo() {
        return this.to;
    }

    public void setTo(final InternetAddress to) {
        this.to = to;
    }

    public InternetAddress getBcc() {
        return this.bcc;
    }

    public void setBcc(final InternetAddress bcc) {
        this.bcc = bcc;
    }

}
