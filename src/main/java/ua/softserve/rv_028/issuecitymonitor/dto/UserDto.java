package ua.softserve.rv_028.issuecitymonitor.dto;

import ua.softserve.rv_028.issuecitymonitor.entity.User;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.UserRole;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.UserStatus;

public class UserDto {

    private static long count;

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

    public UserDto() {}

    public UserDto(User entity) {
        this.id = entity.getId();
        this.userRole = entity.getUserRole();
        this.registrationDate = entity.getRegistrationDate();
        this.firstName = entity.getFirstName();
        this.lastName = entity.getLastName();
        this.password = entity.getPassword();
        this.email = entity.getUsername();
        this.phone = entity.getPhone();
        this.userAgreement = entity.isUserAgreement();
        this.userStatus = entity.getUserStatus();
        this.deleteDate = entity.getDeleteDate();
        this.avatarUrl = entity.getAvatarUrl();
    }

    public UserDto(long id, UserRole userRole, String registrationDate, String firstName, String lastName,
                   String password, String email, String phone, boolean userAgreement, UserStatus userStatus,
                   String deleteDate, String avatarUrl) {
        this.id = id;
        this.userRole = userRole;
        this.registrationDate = registrationDate;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.userAgreement = userAgreement;
        this.userStatus = userStatus;
        this.deleteDate = deleteDate;
        this.avatarUrl = avatarUrl;
    }

    public static long getCount() {
        return count;
    }

    public static void setCount(long count) {
        UserDto.count = count;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        if(userRole == UserRole.ADMIN) {
            count++;
        }
        this.userRole = userRole;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isUserAgreement() {
        return userAgreement;
    }

    public void setUserAgreement(boolean userAgreement) {
        this.userAgreement = userAgreement;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    public String getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(String deleteDate) {
        this.deleteDate = deleteDate;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
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
