package ua.softserve.rv_028.issuecitymonitor.entity;

import javax.persistence.*;

@Entity
@Table(name = "user_vote")
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

    public UserVote() {}

    public UserVote(User user, Petition petition) {
        this.user = user;
        this.petition = petition;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserVote)) return false;

        UserVote userVote = (UserVote) o;

        if (id != userVote.id) return false;
        if (user != null ? !user.equals(userVote.user) : userVote.user != null) return false;
        return petition != null ? petition.equals(userVote.petition) : userVote.petition == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (petition != null ? petition.hashCode() : 0);
        return result;
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
