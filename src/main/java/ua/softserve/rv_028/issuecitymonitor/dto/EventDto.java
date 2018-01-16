package ua.softserve.rv_028.issuecitymonitor.dto;

import lombok.*;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.EventCategory;

@Data
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

}
