package ua.softserve.rv_028.issuecitymonitor.entity;

import javax.persistence.*;

@Entity
@Table(name = "event_attachments")
public class EventAttachment {

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true)
    private long id;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "attachment_url")
    private String attachmentUrl;

    public EventAttachment() {}

    public EventAttachment(Event event, User user, String attachmentUrl) {
        this.event = event;
        this.user = user;
        this.attachmentUrl = attachmentUrl;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAttachmentUrl() {
        return attachmentUrl;
    }

    public void setAttachmentUrl(String attachmentUrl) {
        this.attachmentUrl = attachmentUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EventAttachment)) return false;

        EventAttachment that = (EventAttachment) o;

        if (id != that.id) return false;
        if (event != null ? !event.equals(that.event) : that.event != null) return false;
        if (user != null ? !user.equals(that.user) : that.user != null) return false;
        return attachmentUrl != null ? attachmentUrl.equals(that.attachmentUrl) : that.attachmentUrl == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (event != null ? event.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (attachmentUrl != null ? attachmentUrl.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "EventAttachment{" +
                "id=" + id +
                ", event=" + event +
                ", user=" + user +
                ", attachmentUrl='" + attachmentUrl + '\'' +
                '}';
    }
}
