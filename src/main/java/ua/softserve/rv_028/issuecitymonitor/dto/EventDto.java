package ua.softserve.rv_028.issuecitymonitor.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.EventCategory;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventDto {

    long id;
    UserDto userDto;
    String title;
    String description;
    String initialDate;
    double latitude;
    double longitude;
    String endDate;
    EventCategory category;

<<<<<<< HEAD
    public EventDto() {}

    public EventDto(long id, UserDto userDto, String title, String description, String initialDate, double latitude,
                    double longitude, String endDate, EventCategory category) {
        this.id = id;
        this.userDto = userDto;
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

    @Override
    public String toString() {
        return "EventDto{" +
                "id=" + id +
                ", userDto=" + userDto +
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
=======
}
>>>>>>> afe7edc3b261b97c0c8701d924fe2958e621d506
