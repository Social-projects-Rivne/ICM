package ua.softserve.rv_028.issuecitymonitor.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class IssueLocationDto {

    Long id;
    double latitude;
    double longitude;

}
