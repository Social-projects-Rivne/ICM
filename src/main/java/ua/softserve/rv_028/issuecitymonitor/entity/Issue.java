package ua.softserve.rv_028.issuecitymonitor.entity;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import ua.softserve.rv_028.issuecitymonitor.dto.IssueDto;
import ua.softserve.rv_028.issuecitymonitor.entity.converter.LocalDateTimeConverter;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.IssueCategory;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "issues")
@SQLDelete(sql = "UPDATE issues SET deleted = 'true' WHERE id = ?")
@Where(clause = "deleted <> 'true'")
public class Issue{

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

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "longitude")
    private double longitude;

    @Column(name = "category")
    @Enumerated(EnumType.ORDINAL)
    private IssueCategory category;

    @Column(name = "deleted")
    private boolean isDeleted = false;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "issue", targetEntity = IssueAttachment.class)
    private Set<IssueAttachment> attachments = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "issue", targetEntity = IssueChangeRecord.class)
    private Set<IssueChangeRecord> changeRecords = new HashSet<>();

    public Issue() {
    }

    public Issue(User user, String title, String description, LocalDateTime initialDate, double latitude, double longitude,
                 IssueCategory category) {
        this.user = user;
        this.title = title;
        this.description = description;
        this.initialDate = initialDate;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public IssueCategory getCategory() {
        return category;
    }

    public void setCategory(IssueCategory category) {
        this.category = category;
    }

    public Set<IssueAttachment> getAttachments() {
        return attachments;
    }

    public Set<IssueChangeRecord> getChangeRecords() {
        return changeRecords;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    @PreRemove
    public void delete() {
        this.isDeleted = true;
    }

    @Override
    public String toString() {
        return "Issue{" +
                "id=" + id +
                ", user=" + user.getId() +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", initialDate='" + initialDate + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", category=" + category +
                '}';
    }
}
