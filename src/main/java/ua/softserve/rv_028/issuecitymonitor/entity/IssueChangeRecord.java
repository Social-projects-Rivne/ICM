package ua.softserve.rv_028.issuecitymonitor.entity;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.ChangeRecordStatus;

import javax.persistence.*;

@Entity
@Table(name = "issue_change_records")
@SQLDelete(sql = "UPDATE issue_change_records SET deleted = '1' WHERE id = ?")
@Where(clause = "deleted <> '1'")
public class IssueChangeRecord {

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true)
    private long id;

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
    private int isDeleted = 0;

    public IssueChangeRecord() {}

    public IssueChangeRecord(Issue issue, ChangeRecordStatus changeRecordStatus, User user, String message) {
        this.issue = issue;
        this.changeRecordStatus = changeRecordStatus;
        this.user = user;
        this.message = message;
    }

    public long getId() {
        return id;
    }

    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
    }

    public ChangeRecordStatus getChangeRecordStatus() {
        return changeRecordStatus;
    }

    public void setChangeRecordStatus(ChangeRecordStatus changeRecordStatus) {
        this.changeRecordStatus = changeRecordStatus;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    @PreRemove
    public void delete() {
        this.isDeleted = 1;
    }

    @Override
    public String toString() {
        return "IssueChangeRecord{" +
                "id=" + id +
                ", issue=" + issue +
                ", changeRecordStatus=" + changeRecordStatus +
                ", user=" + user +
                ", message='" + message + '\'' +
                '}';
    }
}
