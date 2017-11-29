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

    public void setId(long id) {
        this.id = id;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PetitionAttachment)) return false;

        PetitionAttachment that = (PetitionAttachment) o;

        if (id != that.id) return false;
        if (petition != null ? !petition.equals(that.petition) : that.petition != null) return false;
        if (user != null ? !user.equals(that.user) : that.user != null) return false;
        return attachmentUrl != null ? attachmentUrl.equals(that.attachmentUrl) : that.attachmentUrl == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (petition != null ? petition.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (attachmentUrl != null ? attachmentUrl.hashCode() : 0);
        return result;
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
