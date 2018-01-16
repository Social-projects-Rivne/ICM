package ua.softserve.rv_028.issuecitymonitor.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;

@Entity
@Table(name = "user_votes")
@SQLDelete(sql = "UPDATE user_votes SET deleted = 'true' WHERE id = ?")
@Getter
@Setter
public class UserVote {

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "petition_id")
    private Petition petition;

    @Column(name = "deleted")
    @Setter(AccessLevel.NONE)
    private boolean isDeleted = false;

    public UserVote() {}

    public UserVote(User user, Petition petition) {
        this.user = user;
        this.petition = petition;
    }

    @PreRemove
    public void delete() {
        this.isDeleted = true;
    }

    @Override
    public String toString() {
        return "UserVote{" +
                "id=" + id +
                ", user=" + user.getId() +
                ", petition=" + petition.getId() +
                '}';
    }
}
