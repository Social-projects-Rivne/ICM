package ua.softserve.rv_028.issuecitymonitor.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;

@Entity
@Table(name = "petition_attachments")
@SQLDelete(sql = "UPDATE petition_attachments SET deleted = 'true' WHERE id = ?")
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PetitionAttachment {

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true)
    long id;

    @ManyToOne
    @JoinColumn(name = "petition_id")
    Petition petition;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @Column(name = "attachment_url")
    String attachmentUrl;

    @Column(name = "deleted")
    @Setter(AccessLevel.NONE)
    boolean isDeleted = false;

    public PetitionAttachment(Petition petition, User user, String attachmentUrl) {
        this.petition = petition;
        this.user = user;
        this.attachmentUrl = attachmentUrl;
    }

    @PreRemove
    public void delete() {
        this.isDeleted = true;
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
