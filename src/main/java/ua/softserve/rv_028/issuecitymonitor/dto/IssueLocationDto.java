package ua.softserve.rv_028.issuecitymonitor.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.IssueCategory;

@NoArgsConstructor
@Getter
@Setter
public class IssueLocationDto {

    private Long id;
    private double latitude;
    private double longitude;

    public IssueLocationDto(Long id, UserDto userDto, String title, String description, String initialDate,
                            double latitude, double longitude, IssueCategory category) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "IssueDto{" +
                "id=" + id + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
