package ua.softserve.rv_028.issuecitymonitor.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ua.softserve.rv_028.issuecitymonitor.controller.PDF.PdfWritable;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.UserRole;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.UserStatus;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@SQLDelete(sql = "UPDATE users SET user_status = '0' WHERE id = ?")
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User implements UserDetails, PdfWritable {

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true)
    Long id;

    @Column(name = "userRole")
    @Enumerated(EnumType.ORDINAL)
    UserRole userRole;

    @Column(name = "reg_date")
    LocalDateTime registrationDate;

    @NotEmpty
    @Column(name = "first_name")
    String firstName;

    @NotEmpty
    @Column(name = "password")
    @Pattern(regexp = "^.{3,}$")
    String password;

    @NotEmpty
    @Column(name = "email", unique = true)
    @Pattern(regexp = "^([\\w.%+-]+)@([\\w-]+\\.)+([\\w]{2,})$")
    String username;

    @NotEmpty
    @Column(name = "last_name")
    String lastName;

    @Column(name = "phone")
    @Pattern(regexp = "^(\\+)+([\\d]{1,4})([\\d]{10})$")
    String phone;

    @Column(name = "user_status")
    @Enumerated(EnumType.ORDINAL)
    UserStatus userStatus;

    @Column(name = "delete_date")
    LocalDateTime deleteDate;

    @Column(name = "avatar_url")
    String avatarUrl;

    @Setter(AccessLevel.NONE)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", targetEntity = Issue.class)
    Set<Issue> issues = new HashSet<>();

    @Setter(AccessLevel.NONE)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", targetEntity = Event.class)
    Set<Event> events = new HashSet<>();

    @Setter(AccessLevel.NONE)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", targetEntity = Petition.class)
    Set<Petition> petitions = new HashSet<>();

    public User(String firstName, String lastName, String password, String username,
                String phone, UserStatus userStatus, UserRole userRole,
                String avatarUrl) {
        this.username = username;
        this.password = password;
        this.userRole = userRole;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.userStatus = userStatus;
        this.avatarUrl = avatarUrl;
    }

    private static boolean checkUserStatus(UserStatus status) {
        return status == UserStatus.ACTIVE || status == UserStatus.UNCONFIRMED;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return UserRole.collectionForRole(this.userRole);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return checkUserStatus(this.getUserStatus());
    }

    @PreRemove
    public void delete() {
        this.deleteDate = LocalDateTime.now();
        this.userStatus = UserStatus.DELETED;
    }

    @PrePersist
    @SuppressWarnings("unused")
    private void insert() {
        this.registrationDate = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userRole=" + userRole +
                ", registrationDate='" + registrationDate + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", phone='" + phone + '\'' +
                ", userStatus=" + userStatus +
                ", deleteDate='" + deleteDate + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                '}';
    }

    @Override
    public String getTitle() {
        return firstName;
    }

    @Override
    public String getDescription() {
        return lastName;
    }

    @Override
    public LocalDateTime getRegDate() {
        return registrationDate;
    }

    @Override
    public String getMail() {
        return username;
    }

    @Override
    public String getPhone() {
        return phone;
    }

    @Override
    public String getRole() {
        return userRole.toString();
    }
}