package ua.softserve.rv_028.issuecitymonitor.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import ua.softserve.rv_028.issuecitymonitor.entity.Event;
import ua.softserve.rv_028.issuecitymonitor.entity.User;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.EventCategory;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class EventDto {

    private static final SimpleDateFormat DB_FORMAT = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
    private static final SimpleDateFormat JSON_FORMAT = new SimpleDateFormat("MM/dd/yyyy HH:mm");

    private long id;

    @JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
    @JsonIdentityReference(alwaysAsId=true)
    private User user;
    private String title;
    private String description;
    private String initialDate;
    private double latitude;
    private double longitude;
    private String endDate;
    private EventCategory category;

    public EventDto(Event entity) {
        this.id = entity.getId();
        this.user = entity.getUser();
        this.title = entity.getTitle();
        this.description = entity.getDescription();
        this.initialDate = formatDate(entity.getInitialDate());
        this.latitude = entity.getLatitude();
        this.longitude = entity.getLongitude();
        this.endDate = entity.getEndDate();
        this.category = entity.getCategory();
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
