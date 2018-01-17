package ua.softserve.rv_028.issuecitymonitor.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;

@Entity
@Table(name = "event_attachments")
@SQLDelete(sql = "UPDATE event_attachments SET deleted = 'true' WHERE id = ?")
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventAttachment {

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true)
    long id;

    @ManyToOne
    @JoinColumn(name = "event_id")
    Event event;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @Column(name = "attachment_url")
    String attachmentUrl;

    @Column(name = "deleted")
    @Setter(AccessLevel.NONE)
    boolean isDeleted = false;

    public EventAttachment(Event event, User user, String attachmentUrl) {
        this.event = event;
        this.user = user;
        this.attachmentUrl = attachmentUrl;
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