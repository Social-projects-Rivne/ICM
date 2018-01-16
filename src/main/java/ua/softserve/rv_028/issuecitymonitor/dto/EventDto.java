package ua.softserve.rv_028.issuecitymonitor.dto;

import lombok.Getter;
import lombok.Setter;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.EventCategory;

@Getter
@Setter
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
