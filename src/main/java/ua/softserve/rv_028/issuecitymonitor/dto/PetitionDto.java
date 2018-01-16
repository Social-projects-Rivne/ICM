package ua.softserve.rv_028.issuecitymonitor.dto;

import lombok.Getter;
import lombok.Setter;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.PetitionCategory;

@Getter
@Setter
public class PetitionDto {

    private long id;
    private UserDto userDto;
    private String title;
    private String description;
    private String initialDate;
    private PetitionCategory category;

    public PetitionDto() {}

    public PetitionDto(long id, UserDto userDto, String title, String description, String initialDate, PetitionCategory category) {
        this.id = id;
        this.userDto = userDto;
        this.title = title;
        this.description = description;
        this.initialDate = initialDate;
        this.category = category;
    }

    @Override
    public String toString() {
        return "PetitionDto{" +
                "id=" + id +
                ", userDto=" + userDto +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", initialDate='" + initialDate + '\'' +
                ", category=" + category +
                '}';
    }
}