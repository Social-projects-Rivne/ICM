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

}
