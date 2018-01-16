package ua.softserve.rv_028.issuecitymonitor.dto;

import lombok.*;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.IssueCategory;

@Data
public class IssueLocationDto {

    private Long id;
    private double latitude;
    private double longitude;

}
