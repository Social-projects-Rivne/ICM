package ua.softserve.rv_028.issuecitymonitor.dto;

import ua.softserve.rv_028.issuecitymonitor.entity.Issue;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.IssueCategory;

public class IssueLocationDto {

    private Long id;
    private double latitude;
    private double longitude;

    public IssueLocationDto() {}


    public IssueLocationDto(Long id, UserDto userDto, String title, String description, String initialDate,
                            double latitude, double longitude, IssueCategory category) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
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
