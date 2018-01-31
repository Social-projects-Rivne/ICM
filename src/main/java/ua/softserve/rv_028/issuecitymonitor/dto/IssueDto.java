package ua.softserve.rv_028.issuecitymonitor.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.IssueCategory;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class IssueDto {

    long id;
    UserDto userDto;
    String title;
    String description;
    String initialDate;
    String photo;
    double latitude;
    double longitude;
    IssueCategory category;

}
