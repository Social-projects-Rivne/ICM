package ua.softserve.rv_028.issuecitymonitor.dto;

import ua.softserve.rv_028.issuecitymonitor.entity.Event;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.EventCategory;

import java.text.ParseException;

import static ua.softserve.rv_028.issuecitymonitor.Constants.DB_FORMAT;
import static ua.softserve.rv_028.issuecitymonitor.Constants.JSON_FORMAT;

public class EventDto {

    private long id;
    private UserDto userDto;
    private String title;
    private String description;
    private String initialDate;
    private double latitude;
    private double longitude;
    private String endDate;
    private EventCategory category;

    public EventDto() {}

    public EventDto(Event entity) {
        this.id = entity.getId();
        this.userDto = new UserDto(entity.getUser());
        this.title = entity.getTitle();
        this.description = entity.getDescription();
        this.initialDate = entity.getInitialDate();
        this.latitude = entity.getLatitude();
        this.longitude = entity.getLongitude();
        this.endDate = entity.getEndDate();
        this.category = entity.getCategory();
    }

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

    private static String formatDate(String date){
        try {
            return JSON_FORMAT.format(DB_FORMAT.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
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
