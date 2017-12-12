package ua.softserve.rv_028.issuecitymonitor.dto;
import ua.softserve.rv_028.issuecitymonitor.entity.Petition;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.PetitionCategory;

public class PetitionDto {

    private long id;
    private UserDto userDto;
    private String title;
    private String description;
    private String initialDate;
    private PetitionCategory category;

    public PetitionDto() {}

    public PetitionDto(Petition entity) {
        this.id = entity.getId();
        this.userDto = new UserDto(entity.getUser());
        this.title = entity.getTitle();
        this.description = entity.getDescription();
        this.initialDate = entity.getInitialDate();
        this.category = entity.getCategory();
    }

    public PetitionDto(long id, UserDto userDto, String title, String description, String initialDate, PetitionCategory category) {
        this.id = id;
        this.userDto = userDto;
        this.title = title;
        this.description = description;
        this.initialDate = initialDate;
        this.category = category;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(String initialDate) {
        this.initialDate = initialDate;
    }


    public PetitionCategory getCategory() {
        return category;
    }

    public void setCategory(PetitionCategory category) {
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