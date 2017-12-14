package ua.softserve.rv_028.issuecitymonitor.dto;


import ua.softserve.rv_028.issuecitymonitor.entity.Event;
import ua.softserve.rv_028.issuecitymonitor.entity.Issue;
import ua.softserve.rv_028.issuecitymonitor.entity.Petition;

import ua.softserve.rv_028.issuecitymonitor.entity.User;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.UserRole;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.UserStatus;

import java.util.HashSet;
import java.util.Set;

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
    private boolean isDeleted;
    /*private Set<Issue> issues = new HashSet<>();
    private Set<Event> events = new HashSet<>();
    private Set<Petition> petitions = new HashSet<>();*/

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
        this.isDeleted = entity.getIsDeleted();
       /* this.issues = entity.getIssues();
        this.events = entity.getEvents();
        this.petitions = entity.getPetitions();*/
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserRole getUserRole() {
        if(userRole == UserRole.ADMIN) {
            count++;
        }
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
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

      public static long getCount() {
        return count;
    }

    public static void setCount(long k) {
        count = k;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted() {
        isDeleted = true;
    }
    /*
    public Set<Issue> getIssues() {
        return issues;
    }

    public void setIssues(Set<Issue> issues) {
        this.issues = issues;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    public Set<Petition> getPetitions() {
        return petitions;
    }

    public void setPetitions(Set<Petition> petitions) {
        this.petitions = petitions;
    }

    */



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

