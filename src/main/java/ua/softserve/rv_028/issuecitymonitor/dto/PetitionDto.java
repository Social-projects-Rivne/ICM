package ua.softserve.rv_028.issuecitymonitor.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.PetitionCategory;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PetitionDto {

    long id;
    UserDto userDto;
    String title;
    String description;
    String initialDate;
    PetitionCategory category;

}