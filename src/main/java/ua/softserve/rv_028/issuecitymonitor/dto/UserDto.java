package ua.softserve.rv_028.issuecitymonitor.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.UserRole;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.UserStatus;

@NoArgsConstructor
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {

    long id;
    UserRole userRole;
    String registrationDate;
    String firstName;
    String lastName;
    String password;
    String email;
    String phone;
    boolean userAgreement;
    UserStatus userStatus;
    String deleteDate;
    String avatarUrl;

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

}
