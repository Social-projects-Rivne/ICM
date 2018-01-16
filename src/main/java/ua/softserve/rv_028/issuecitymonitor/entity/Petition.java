package ua.softserve.rv_028.issuecitymonitor.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import ua.softserve.rv_028.issuecitymonitor.entity.converter.LocalDateTimeConverter;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.PetitionCategory;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "petitions")
@SQLDelete(sql = "UPDATE petitions SET deleted = 'true' WHERE id = ?")
@Where(clause = "deleted <> true")
@NoArgsConstructor
@Getter
@Setter
public class Petition{

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "initial_date")
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime initialDate;

    @Column(name = "category")
    private PetitionCategory category;

    @Column(name = "deleted")
    @Setter(AccessLevel.NONE)
    private boolean isDeleted = false;

    @Column(name = "creation_date")
    @Setter(AccessLevel.NONE)
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime creationDate;

    @Setter(AccessLevel.NONE)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "petition", targetEntity = PetitionAttachment.class, cascade = CascadeType.REMOVE)
    private Set<PetitionAttachment> attachments = new HashSet<>();

    @Setter(AccessLevel.NONE)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "petition", targetEntity = PetitionChangeRecord.class, cascade = CascadeType.REMOVE)
    private Set<PetitionChangeRecord> changeRecords = new HashSet<>();

    public Petition(User user, String title, String description, LocalDateTime initialDate, PetitionCategory category) {
        this.user = user;
        this.title = title;
        this.description = description;
        this.initialDate = initialDate;
        this.category = category;
    }

    @PrePersist
    private void insert() {
        this.creationDate = LocalDateTime.now();
    }

    @PreRemove
    public void delete() {
        this.isDeleted = true;
    }

    @Override
    public String toString() {
        return "Petition{" +
                "id=" + id +
                ", user=" + user.getId() +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", initialDate='" + initialDate + '\'' +
                ", category=" + category +
                '}';
    }
}
