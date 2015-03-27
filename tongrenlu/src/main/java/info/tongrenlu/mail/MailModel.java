package info.tongrenlu.mail;

import java.util.HashMap;
import java.util.Map;

public class MailModel {

    private String subject;

    private String from;

    private String to;

    private String bcc;

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

    public String getTo() {
        return this.to;
    }

    public void setTo(final String to) {
        this.to = to;
    }

    public String getBcc() {
        return this.bcc;
    }

    public void setBcc(final String bcc) {
        this.bcc = bcc;
    }

    public String getFrom() {
        return this.from;
    }

    public void setFrom(final String from) {
        this.from = from;
    }

}
