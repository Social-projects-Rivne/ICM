package ua.softserve.rv_028.issuecitymonitor.entity;

import javax.persistence.*;

@Entity
@Table(name = "user_votes")
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
    private boolean isDeleted = false;

    public UserVote() {}

    public UserVote(User user, Petition petition) {
        this.user = user;
        this.petition = petition;
    }

    public void setId(long id) {
        this.id = id;
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

    public boolean getIsDeleted() {
        return isDeleted;
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
