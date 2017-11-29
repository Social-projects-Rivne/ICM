package ua.softserve.rv_028.issuecitymonitor.entity;

import javax.persistence.*;

@Entity
@Table(name = "issue_attachment")
public class IssueAttachment {

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true)
    private long id;

    @ManyToOne
    @JoinColumn(name = "issue_id")
    private Issue issue;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "attachment_url")
    private String attachmentUrl;

    public IssueAttachment() {}

    public IssueAttachment(Issue issue, User user, String attachmentUrl) {
        this.issue = issue;
        this.user = user;
        this.attachmentUrl = attachmentUrl;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
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
        if (!(o instanceof IssueAttachment)) return false;

        IssueAttachment that = (IssueAttachment) o;

        if (id != that.id) return false;
        if (issue != null ? !issue.equals(that.issue) : that.issue != null) return false;
        if (user != null ? !user.equals(that.user) : that.user != null) return false;
        return attachmentUrl != null ? attachmentUrl.equals(that.attachmentUrl) : that.attachmentUrl == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (issue != null ? issue.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (attachmentUrl != null ? attachmentUrl.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "IssueAttachment{" +
                "id=" + id +
                ", issue=" + issue +
                ", user=" + user +
                ", attachmentUrl='" + attachmentUrl + '\'' +
                '}';
    }
}
