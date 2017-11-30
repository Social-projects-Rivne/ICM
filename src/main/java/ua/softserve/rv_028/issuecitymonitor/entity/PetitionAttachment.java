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
