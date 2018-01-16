package ua.softserve.rv_028.issuecitymonitor.dto;

import lombok.*;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.IssueCategory;

@Data
public class IssueDto {

    private long id;
    private UserDto userDto;
    private String title;
    private String description;
    private String initialDate;
    private double latitude;
    private double longitude;
    private IssueCategory category;

}
