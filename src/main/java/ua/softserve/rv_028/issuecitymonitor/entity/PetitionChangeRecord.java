package ua.softserve.rv_028.issuecitymonitor.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.SQLDelete;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.ChangeRecordStatus;

import javax.persistence.*;

@Table
@Entity(name = "petition_change_records")
@SQLDelete(sql = "UPDATE petition_change_records SET deleted = 'true' WHERE id = ?")
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PetitionChangeRecord {

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true)
    long id;

    @ManyToOne
    @JoinColumn(name = "petition_id")
    Petition petition;

    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
    ChangeRecordStatus changeRecordStatus;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @Column(name = "message")
    String message;

    @Column(name = "deleted")
    @Setter(AccessLevel.NONE)
    boolean isDeleted = false;

    public PetitionChangeRecord(Petition petition, ChangeRecordStatus changeRecordStatus, User user, String message) {
        this.petition = petition;
        this.changeRecordStatus = changeRecordStatus;
        this.user = user;
        this.message = message;
    }

    @PreRemove
    public void delete() {
        this.isDeleted = true;
    }

    @Override
    public String toString() {
        return "PetitionChangeRecord{" +
                "id=" + id +
                ", petition=" + petition.getId() +
                ", changeRecordStatus=" + changeRecordStatus +
                ", user=" + user.getId() +
                ", message='" + message + '\'' +
                '}';
    }
}
