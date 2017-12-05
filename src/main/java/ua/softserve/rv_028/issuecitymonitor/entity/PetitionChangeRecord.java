package ua.softserve.rv_028.issuecitymonitor.entity;

import ua.softserve.rv_028.issuecitymonitor.entity.enums.ChangeRecordStatus;

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
