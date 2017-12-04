package ua.softserve.rv_028.issuecitymonitor.entity;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table(name = "petition_attachments")
@SQLDelete(sql = "UPDATE petition_attachments SET deleted = '1' WHERE id = ?")
@Where(clause = "deleted <> '1'")
public class PetitionAttachment {

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true)
    private long id;

    @ManyToOne
    @JoinColumn(name = "petition_id")
    private Petition petition;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "attachment_url")
    private String attachmentUrl;

    @Column(name = "deleted")
    private int isDeleted = 0;

    public PetitionAttachment() {}

    public PetitionAttachment(Petition petition, User user, String attachmentUrl) {
        this.petition = petition;
        this.user = user;
        this.attachmentUrl = attachmentUrl;
    }

    public long getId() {
        return id;
    }

    public Petition getPetition() {
        return petition;
    }

    public void setPetition(Petition petition) {
        this.petition = petition;
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

    public int getIsDeleted() {
        return isDeleted;
    }

    @PreRemove
    public void delete() {
        this.isDeleted = 1;
    }

    @Override
    public String toString() {
        return "PetitionAttachment{" +
                "id=" + id +
                ", petition=" + petition +
                ", user=" + user +
                ", attachmentUrl='" + attachmentUrl + '\'' +
                '}';
    }
}
