package ua.softserve.rv_028.issuecitymonitor.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import ua.softserve.rv_028.issuecitymonitor.controller.PDF.PdfWritable;
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
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Petition implements PdfWritable {

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true)
    Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @Column(name = "title")
    String title;

    @Column(name = "description")
    String description;

    @Column(name = "initial_date")
    @Convert(converter = LocalDateTimeConverter.class)
    LocalDateTime initialDate;

    @Column(name = "category")
    PetitionCategory category;

    @Column(name = "deleted")
    @Setter(AccessLevel.NONE)
    boolean isDeleted = false;

    @Column(name = "creation_date")
    @Setter(AccessLevel.NONE)
    @Convert(converter = LocalDateTimeConverter.class)
    LocalDateTime creationDate;

    @Setter(AccessLevel.NONE)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "petition", targetEntity = PetitionAttachment.class, cascade = CascadeType.REMOVE)
    Set<PetitionAttachment> attachments = new HashSet<>();

    @Setter(AccessLevel.NONE)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "petition", targetEntity = PetitionChangeRecord.class, cascade = CascadeType.REMOVE)
    Set<PetitionChangeRecord> changeRecords = new HashSet<>();

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
    public String getCat() {
        return category.toString();
    }

    @Override
    public User getUserDto() {
        return user;
    }

    @Override
    public String getMail() {
        return null;
    }

    @Override
    public String getPhone() {
        return null;
    }

    @Override
    public LocalDateTime getRegDate() {
        return null;
    }

    @Override
    public String getRole() {
        return null;
    }

}
