package ua.softserve.rv_028.issuecitymonitor.entity;

import ua.softserve.rv_028.issuecitymonitor.model.enums.ChangeRecordStatus;

import javax.persistence.*;

@Table
@Entity(name = "petition_change_records")
public class PetitionChangeRecord {

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true)
    private long id;

    @ManyToOne
    @JoinColumn(name = "petition_id")
    private Petition petition;

    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
    private ChangeRecordStatus changeRecordStatus;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "message")
    private String message;

    public PetitionChangeRecord() {}

    public PetitionChangeRecord(Petition petition, ChangeRecordStatus changeRecordStatus, User user, String message) {
        this.petition = petition;
        this.changeRecordStatus = changeRecordStatus;
        this.user = user;
        this.message = message;
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

    public ChangeRecordStatus getChangeRecordStatus() {
        return changeRecordStatus;
    }

    public void setChangeRecordStatus(ChangeRecordStatus changeRecordStatus) {
        this.changeRecordStatus = changeRecordStatus;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PetitionChangeRecord)) return false;

        PetitionChangeRecord that = (PetitionChangeRecord) o;

        if (id != that.id) return false;
        if (petition != null ? !petition.equals(that.petition) : that.petition != null) return false;
        if (changeRecordStatus != that.changeRecordStatus) return false;
        if (user != null ? !user.equals(that.user) : that.user != null) return false;
        return message != null ? message.equals(that.message) : that.message == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (petition != null ? petition.hashCode() : 0);
        result = 31 * result + (changeRecordStatus != null ? changeRecordStatus.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PetitionChangeRecord{" +
                "id=" + id +
                ", petition=" + petition +
                ", changeRecordStatus=" + changeRecordStatus +
                ", user=" + user +
                ", message='" + message + '\'' +
                '}';
    }
}
