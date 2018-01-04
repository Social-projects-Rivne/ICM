package ua.softserve.rv_028.issuecitymonitor.entity;

import javax.persistence.*;

@Entity
@Table(name = "petition_attachments")
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
    private boolean isDeleted = false;

    public PetitionAttachment() {}

    public PetitionAttachment(Petition petition, User user, String attachmentUrl) {
        this.petition = petition;
        this.user = user;
        this.attachmentUrl = attachmentUrl;
    }

    public void setId(long id) {
        this.id = id;
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

    public boolean getIsDeleted() {
        return isDeleted;
    }

    @Override
    public String toString() {
        return "PetitionAttachment{" +
                "id=" + id +
                ", petition=" + petition.getId() +
                ", user=" + user.getId() +
                ", attachmentUrl='" + attachmentUrl + '\'' +
                '}';
    }
}
