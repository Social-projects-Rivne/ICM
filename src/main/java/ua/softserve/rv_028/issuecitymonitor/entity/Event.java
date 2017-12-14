package ua.softserve.rv_028.issuecitymonitor.entity;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import ua.softserve.rv_028.issuecitymonitor.dto.EventDto;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.EventCategory;

import javax.persistence.*;

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
    @OrderBy
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "initial_date")
    private String initialDate;

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "longitude")
    private double longitude;

    @Column(name = "end_date")
    private String endDate;

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

    public Event(EventDto eventDto) {
        this.user = new User(eventDto.getUserDto());
        this.title = eventDto.getTitle();
        this.description = eventDto.getDescription();
        this.initialDate = eventDto.getInitialDate();
        this.latitude = eventDto.getLatitude();
        this.longitude = eventDto.getLongitude();
        this.endDate = eventDto.getEndDate();
        this.category = eventDto.getCategory();
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

    public String getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(String initialDate) {
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

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
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

    public Set<EventAttachment> getAttachments() {
        return attachments;
    }

    @PreRemove
    public void delete() {
        this.isDeleted = true;
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