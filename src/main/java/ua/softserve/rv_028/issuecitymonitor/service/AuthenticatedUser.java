package ua.softserve.rv_028.issuecitymonitor.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.UserRole;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.UserStatus;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class AuthenticatedUser extends User{

    private String email;
    private String password;
    private UserRole userRole;
    private UserStatus status;
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticatedUser.class.getName());

    AuthenticatedUser(@NotNull ua.softserve.rv_028.issuecitymonitor.entity.User user){
        super(user.getEmail(), user.getPassword(), true, true, true,
                isNotLocked(user.getUserStatus()), UserRole.collectionForRole(user.getRole()));

        checkRoleIsNull(user.getRole());
        checkStatusIsNull(user.getUserStatus());

        this.email = user.getEmail();
        this.password = user.getPassword();
        this.userRole = user.getRole();
        this.status = user.getUserStatus();
    }

    private void checkStatusIsNull(UserStatus status) {
        if (status == null){
            LOGGER.error("Constructor got a null UserStatus enum");
            throw new IllegalArgumentException("Cannot pass null User Status to constructor");
        }
    }

    private void checkRoleIsNull(UserRole userRole) {
        if (userRole == null) {
            LOGGER.error("Constructor got a null UserRole enum");
            throw new IllegalArgumentException("Cannot pass null User UserRole to constructor");
        }
    }

    private static boolean isNotLocked(UserStatus status) {
        return status == UserStatus.ACTIVE || status == UserStatus.UNCONFIRMED;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    boolean equals(ua.softserve.rv_028.issuecitymonitor.entity.User user){
        return (Objects.equals(user.getEmail(), email)) && (Objects.equals(user.getPassword(), password))
                && (user.getRole() == userRole) && (user.getUserStatus() == status);
    }

    public AuthenticatedUser setUser(@NotNull ua.softserve.rv_028.issuecitymonitor.entity.User user){

        email = user.getEmail();
        password = user.getPassword();
        userRole = user.getRole();
        status = user.getUserStatus();
        return this;
    }
}