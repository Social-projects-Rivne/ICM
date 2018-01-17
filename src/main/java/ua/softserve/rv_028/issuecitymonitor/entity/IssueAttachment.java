package ua.softserve.rv_028.issuecitymonitor.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;

@Entity
@Table(name = "issue_attachments")
@SQLDelete(sql = "UPDATE issue_attachments SET deleted = 'true' WHERE id = ?")
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class IssueAttachment {

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true)
    Long id;

    @ManyToOne
    @JoinColumn(name = "issue_id")
    Issue issue;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @Column(name = "attachment_url")
    String attachmentUrl;

    @Column(name = "deleted")
    @Setter(AccessLevel.NONE)
    boolean isDeleted = false;

    public IssueAttachment(Issue issue, User user, String attachmentUrl) {
        this.issue = issue;
        this.user = user;
        this.attachmentUrl = attachmentUrl;
    }

    @PreRemove
    public void delete() {
        this.isDeleted = true;
    }

    @Override
    public String toString() {
        return "IssueAttachment{" +
                "id=" + id +
                ", issue=" + issue.getId() +
                ", user=" + user.getId() +
                ", attachmentUrl='" + attachmentUrl + '\'' +
                '}';
    }
}