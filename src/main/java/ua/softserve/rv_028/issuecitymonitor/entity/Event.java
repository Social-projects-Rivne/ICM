package ua.softserve.rv_028.issuecitymonitor.entity;

import ua.softserve.rv_028.issuecitymonitor.entity.enums.EventCategory;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "events")
public class Event{

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "event", targetEntity = EventAttachment.class)
    private Set<EventAttachment> attachments = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "event", targetEntity = EventChangeRecord.class)
    private Set<EventChangeRecord> changeRecords = new HashSet<>();

    public Event() {
        super();
    }

    public Event(User user, String title, String description, String initialDate, double latitude, double longitude,
                 String endDate, EventCategory category) {
        this.user = user;
        this.title = title;
        this.description = description;
        this.initialDate = initialDate;
        this.latitude = latitude;
        this.longitude = longitude;
        this.endDate = endDate;
        this.category = category;
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

    public Set<EventAttachment> getAttachments() {
        return attachments;
    }

    public Set<EventChangeRecord> getChangeRecords() {
        return changeRecords;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Event)) return false;

        Event event = (Event) o;

        if (id != event.id) return false;
        if (Double.compare(event.latitude, latitude) != 0) return false;
        if (Double.compare(event.longitude, longitude) != 0) return false;
        if (user != null ? !user.equals(event.user) : event.user != null) return false;
        if (title != null ? !title.equals(event.title) : event.title != null) return false;
        if (description != null ? !description.equals(event.description) : event.description != null) return false;
        if (initialDate != null ? !initialDate.equals(event.initialDate) : event.initialDate != null) return false;
        if (endDate != null ? !endDate.equals(event.endDate) : event.endDate != null) return false;
        return category == event.category;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (id ^ (id >>> 32));
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (initialDate != null ? initialDate.hashCode() : 0);
        temp = Double.doubleToLongBits(latitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(longitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", user=" + user +
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
