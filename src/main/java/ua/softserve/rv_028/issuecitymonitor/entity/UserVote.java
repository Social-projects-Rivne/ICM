package ua.softserve.rv_028.issuecitymonitor.entity;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table(name = "user_votes")
@SQLDelete(sql = "UPDATE user_votes SET deleted = '1' WHERE id = ?")
@Where(clause = "deleted <> '1'")
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
    private int isDeleted = 0;

    public UserVote() {}

    public UserVote(User user, Petition petition) {
        this.user = user;
        this.petition = petition;
    }

    public long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Petition getPetition() {
        return petition;
    }

    public void setPetition(Petition petition) {
        this.petition = petition;
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
        return "UserVote{" +
                "id=" + id +
                ", user=" + user +
                ", petition=" + petition +
                '}';
    }
}
