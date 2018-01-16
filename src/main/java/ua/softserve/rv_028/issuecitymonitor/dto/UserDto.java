package ua.softserve.rv_028.issuecitymonitor.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.UserRole;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.UserStatus;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

    private long id;
    private UserRole userRole;
    private String registrationDate;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String phone;
    private boolean userAgreement;
    private UserStatus userStatus;
    private String deleteDate;
    private String avatarUrl;

    public UserDto(String firstName, String lastName, String email, String password){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public UserDto(long id, UserRole userRole, String firstName, String lastName, String password, String email,
                   String phone, boolean userAgreement, UserStatus userStatus, String avatarUrl) {
        this.id = id;
        this.userRole = userRole;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.userAgreement = userAgreement;
        this.userStatus = userStatus;
        this.avatarUrl = avatarUrl;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", userRole=" + userRole +
                ", registrationDate='" + registrationDate + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", userAgreement=" + userAgreement +
                ", userStatus=" + userStatus +
                ", deleteDate='" + deleteDate + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                '}';
    }
}
