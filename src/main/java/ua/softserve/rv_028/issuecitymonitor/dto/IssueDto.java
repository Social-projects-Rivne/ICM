package ua.softserve.rv_028.issuecitymonitor.dto;

import lombok.Getter;
import lombok.Setter;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.IssueCategory;

@Getter
@Setter
public class IssueDto {

    private long id;
    private UserDto userDto;
    private String title;
    private String description;
    private String initialDate;
    private double latitude;
    private double longitude;
    private IssueCategory category;

    public IssueDto(long id, UserDto userDto, String title, String description, String initialDate,
                    double latitude, double longitude, IssueCategory category) {
        this.id = id;
        this.userDto = userDto;
        this.title = title;
        this.description = description;
        this.initialDate = initialDate;
        this.latitude = latitude;
        this.longitude = longitude;
        this.category = category;
    }

    public IssueDto() {}

    @Override
    public String toString() {
        return "IssueDto{" +
                "id=" + id +
                ", userDto=" + userDto +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", initialDate='" + initialDate + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", category=" + category +
                '}';
    }
}
