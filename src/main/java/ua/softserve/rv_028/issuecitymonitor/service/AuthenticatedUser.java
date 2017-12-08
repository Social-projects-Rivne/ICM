package ua.softserve.rv_028.issuecitymonitor.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.Role;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.UserStatus;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

public class AuthenticatedUser extends User{

    private String email;
    private String password;
    private Role role;
    private UserStatus status;
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticatedUser.class.getName());

    AuthenticatedUser(@NotNull ua.softserve.rv_028.issuecitymonitor.entity.User user){
        super(user.getEmail(), user.getPassword(), true, true, true,
                isNotLocked(user.getUserStatus()), checkRole(user.getRole()));

        checkRoleIsNull(user.getRole());
        checkStatusIsNull(user.getUserStatus());

        this.email = user.getEmail();
        this.password = user.getPassword();
        this.role = user.getRole();
        this.status = user.getUserStatus();
    }

    private void checkStatusIsNull(UserStatus status) {
        if (status == null){
            LOGGER.error("Constructor got a null UserStatus enum");
            throw new IllegalArgumentException("Cannot pass null User Status to constructor");
        }
    }

    private void checkRoleIsNull(Role role) {
        if (role == null) {
            LOGGER.error("Constructor got a null Role enum");
            throw new IllegalArgumentException("Cannot pass null User Role to constructor");
        }
    }

    private static boolean isNotLocked(UserStatus status) {
        return status == UserStatus.ACTIVE || status == UserStatus.UNCONFIRMED;
    }

    private static Collection<? extends GrantedAuthority> checkRole(Role role) {
        HashSet<GrantedAuthority> roles = new HashSet<>();
        if (role == Role.USER) {
            roles.add(new SimpleGrantedAuthority(Role.USER.name()));
        } else if (role == Role.MODERATOR){
            roles.add(new SimpleGrantedAuthority(Role.USER.name()));
            roles.add(new SimpleGrantedAuthority(Role.MODERATOR.name()));
        } else if (role == Role.ADMIN){
            roles.add(new SimpleGrantedAuthority(Role.USER.name()));
            roles.add(new SimpleGrantedAuthority(Role.MODERATOR.name()));
            roles.add(new SimpleGrantedAuthority(Role.ADMIN.name()));
        }
        return roles;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    boolean equals(ua.softserve.rv_028.issuecitymonitor.entity.User user){
        return (Objects.equals(user.getEmail(), email)) && (Objects.equals(user.getPassword(), password))
                && (user.getRole() == role) && (user.getUserStatus() == status);
    }

    public AuthenticatedUser setUser(@NotNull ua.softserve.rv_028.issuecitymonitor.entity.User user){

        email = user.getEmail();
        password = user.getPassword();
        role = user.getRole();
        status = user.getUserStatus();
        return this;
    }
}