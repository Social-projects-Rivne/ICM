package ua.softserve.rv_028.issuecitymonitor.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.ChangeRecordStatus;

import javax.persistence.*;

@Entity
@Table(name = "issue_change_records")
@SQLDelete(sql = "UPDATE issue_change_records SET deleted = 'true' WHERE id = ?")
@Getter
@Setter
public class IssueChangeRecord {

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "issue_id")
    private Issue issue;

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

    public IssueChangeRecord() {}

    public IssueChangeRecord(Issue issue, ChangeRecordStatus changeRecordStatus, User user, String message) {
        this.issue = issue;
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
        return "IssueChangeRecord{" +
                "id=" + id +
                ", issue=" + issue.getId() +
                ", changeRecordStatus=" + changeRecordStatus +
                ", user=" + user.getId() +
                ", message='" + message + '\'' +
                '}';
    }
}