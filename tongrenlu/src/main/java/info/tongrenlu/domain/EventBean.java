package info.tongrenlu.domain;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class EventBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4582384245453494173L;

    private String eventId = null;

    private String eventName = null;

    private Date eventDate = null;

    public String getEventId() {
        return this.eventId;
    }

    public void setEventId(final String eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return this.eventName;
    }

    public void setEventName(final String eventName) {
        this.eventName = eventName;
    }

    public Date getEventDate() {
        return this.eventDate;
    }

    public void setEventDate(final Date eventDate) {
        this.eventDate = eventDate;
    }

}
