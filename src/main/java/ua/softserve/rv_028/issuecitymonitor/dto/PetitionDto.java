package ua.softserve.rv_028.issuecitymonitor.dto;

import lombok.*;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.PetitionCategory;

@Data
public class PetitionDto {

    private long id;
    private UserDto userDto;
    private String title;
    private String description;
    private String initialDate;
    private PetitionCategory category;

}