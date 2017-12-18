package ua.softserve.rv_028.issuecitymonitor.entity;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import ua.softserve.rv_028.issuecitymonitor.dto.EventDto;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.EventCategory;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "events")
@SQLDelete(sql = "UPDATE events SET deleted = 'true' WHERE id = ?")
@Where(clause = "deleted <> 'true'")
public class Event {

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
    private LocalDateTime initialDate;

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "longitude")
    private double longitude;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "category")
    @Enumerated(EnumType.ORDINAL)
    private EventCategory category;

    @Column(name = "deleted")
    private boolean isDeleted = false;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "event", targetEntity = EventAttachment.class)
    private Set<EventAttachment> attachments = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "event", targetEntity = EventChangeRecord.class)
    private Set<EventChangeRecord> changeRecords = new HashSet<>();

    public Event() {}

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(LocalDateTime initialDate) {
        this.initialDate = initialDate;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public EventCategory getCategory() {
        return category;
    }

    public void setCategory(EventCategory category) {
        this.category = category;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    @PreRemove
    public void delete() {
        this.isDeleted = true;
    }

    public Set<EventAttachment> getAttachments() {
        return attachments;
    }

    public Set<EventChangeRecord> getChangeRecords() {
        return changeRecords;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", user=" + user.getId() +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", initialDate='" + initialDate + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", endDate='" + endDate + '\'' +
                ", category=" + category +
                '}';
    }
}