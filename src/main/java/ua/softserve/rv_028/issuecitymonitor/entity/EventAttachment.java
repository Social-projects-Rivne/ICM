package ua.softserve.rv_028.issuecitymonitor.entity;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table(name = "event_attachments")
@SQLDelete(sql = "UPDATE event_attachments SET deleted = 'true' WHERE id = ?")
@Where(clause = "deleted <> 'true'")
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

    @Column(name = "deleted")
    private boolean isDeleted = false;

    public EventAttachment() {}

    public EventAttachment(Event event, User user, String attachmentUrl) {
        this.event = event;
        this.user = user;
        this.attachmentUrl = attachmentUrl;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
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

    public boolean getIsDeleted() {
        return isDeleted;
    }

    @PreRemove
    public void delete() {
        this.isDeleted = true;
    }

    @Override
    public String toString() {
        return "EventAttachment{" +
                "id=" + id +
                ", event=" + event.getId() +
                ", user=" + user.getId() +
                ", attachmentUrl='" + attachmentUrl + '\'' +
                '}';
    }
}
