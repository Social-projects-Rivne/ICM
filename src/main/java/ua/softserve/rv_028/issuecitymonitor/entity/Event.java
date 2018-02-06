package ua.softserve.rv_028.issuecitymonitor.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import ua.softserve.rv_028.issuecitymonitor.controller.PDF.PdfWritable;
import ua.softserve.rv_028.issuecitymonitor.entity.converter.LocalDateTimeConverter;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.EventCategory;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "events")
@SQLDelete(sql = "UPDATE events SET deleted = 'true' WHERE id = ?")
@Where(clause = "deleted <> true")
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Event implements PdfWritable {

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true)
    @OrderBy
    Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @Column(name = "title")
    String title;

    @Column(name = "description")
    String description;

    @Column(name = "initial_date")
    @Convert(converter = LocalDateTimeConverter.class)
    LocalDateTime initialDate;

    @Column(name = "latitude")
    double latitude;

    @Column(name = "longitude")
    double longitude;

    @Column(name = "end_date")
    @Convert(converter = LocalDateTimeConverter.class)
    LocalDateTime endDate;

    @Column(name = "category")
    @Enumerated(EnumType.ORDINAL)
    EventCategory category;

    @Column(name = "deleted")
    @Setter(AccessLevel.NONE)
    boolean isDeleted = false;

    @Column(name = "creation_date")
    @Setter(AccessLevel.NONE)
    @Convert(converter = LocalDateTimeConverter.class)
    LocalDateTime creationDate;

    @Setter(AccessLevel.NONE)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "event", targetEntity = EventAttachment.class, cascade = CascadeType.REMOVE)
    Set<EventAttachment> attachments = new HashSet<>();

    @Setter(AccessLevel.NONE)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "event", targetEntity = EventChangeRecord.class, cascade = CascadeType.REMOVE)
    Set<EventChangeRecord> changeRecords = new HashSet<>();

    public Event(User user, String title, String description, LocalDateTime initialDate, double latitude, double longitude,
                 LocalDateTime endDate, EventCategory category) {
        this.user = user;
        this.title = title;
        this.description = description;
        this.initialDate = initialDate;
        this.latitude = latitude;
        this.longitude = longitude;
        this.endDate = endDate;
        this.category = category;
    }

    @PrePersist
    @SuppressWarnings("unused")
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
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}