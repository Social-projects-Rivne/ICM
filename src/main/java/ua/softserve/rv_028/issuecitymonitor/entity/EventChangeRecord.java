package ua.softserve.rv_028.issuecitymonitor.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.ChangeRecordStatus;

import javax.persistence.*;

@Entity
@Table(name = "event_change_records")
@SQLDelete(sql = "UPDATE event_change_records SET deleted = 'true' WHERE id = ?")
@NoArgsConstructor
@Getter
@Setter
public class EventChangeRecord {

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true)
    private long id;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
    private ChangeRecordStatus changeRecordStatus;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "message")
    private String message;

    @Column(name = "deleted")
    @Setter(AccessLevel.NONE)
    private boolean isDeleted = false;

    public EventChangeRecord(Event event, ChangeRecordStatus changeRecordStatus, User user, String message) {
        this.event = event;
        this.changeRecordStatus = changeRecordStatus;
        this.user = user;
        this.message = message;
    }

    @PreRemove
    public void delete() {
        this.isDeleted = true;
    }

    @Override
    public String toString() {
        return "EventChangeRecord{" +
                "id=" + id +
                ", event=" + event.getId() +
                ", changeRecordStatus=" + changeRecordStatus +
                ", user=" + user.getId() +
                ", message='" + message + '\'' +
                '}';
    }
}