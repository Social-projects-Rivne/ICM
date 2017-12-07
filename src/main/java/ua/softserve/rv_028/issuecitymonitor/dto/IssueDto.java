package ua.softserve.rv_028.issuecitymonitor.dto;

import ua.softserve.rv_028.issuecitymonitor.entity.Issue;
import ua.softserve.rv_028.issuecitymonitor.entity.IssueAttachment;
import ua.softserve.rv_028.issuecitymonitor.entity.IssueChangeRecord;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.IssueCategory;

import java.util.HashSet;
import java.util.Set;

public class IssueDto {

    private long id;
    private UserDto userDto;
    private String title;
    private String description;
    private String initialDate;
    private double latitude;
    private double longitude;
    private IssueCategory category;
    private Set<IssueAttachment> attachments = new HashSet<>();
    private Set<IssueChangeRecord> changeRecords = new HashSet<>();

    public IssueDto(Issue entity) {
        this.id = entity.getId();
        this.userDto = new UserDto(entity.getUser());
        this.title = entity.getTitle();
        this.description = entity.getDescription();
        this.initialDate = entity.getInitialDate();
        this.latitude = entity.getLatitude();
        this.longitude = entity.getLongitude();
        this.category = entity.getCategory();
    }

    public IssueDto(long id, UserDto userDto, String title, String description, String initialDate, double latitude, double longitude, IssueCategory category, Set<IssueAttachment> attachments, Set<IssueChangeRecord> changeRecords) {
        this.id = id;
        this.userDto = userDto;
        this.title = title;
        this.description = description;
        this.initialDate = initialDate;
        this.latitude = latitude;
        this.longitude = longitude;
        this.category = category;
        this.attachments = attachments;
        this.changeRecords = changeRecords;
    }

    public IssueDto() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
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

    public IssueCategory getCategory() {
        return category;
    }

    public void setCategory(IssueCategory category) {
        this.category = category;
    }

    public Set<IssueAttachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(Set<IssueAttachment> attachments) {
        this.attachments = attachments;
    }

    public Set<IssueChangeRecord> getChangeRecords() {
        return changeRecords;
    }

    public void setChangeRecords(Set<IssueChangeRecord> changeRecords) {
        this.changeRecords = changeRecords;
    }

    @Override
    public String toString() {
        return "IssueDto{" +
                "id=" + id +
                ", userDto=" + userDto +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", initialDate='" + initialDate + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", category=" + category +
                ", attachments=" + attachments +
                ", changeRecords=" + changeRecords +
                '}';
    }
}
