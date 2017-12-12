package ua.softserve.rv_028.issuecitymonitor.models;

import org.springframework.security.core.GrantedAuthority;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ua.softserve.rv_028.issuecitymonitor.entity.User;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.UserRole;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.UserStatus;

import java.util.*;

public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {

    private String username;
    private String password;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private Collection<GrantedAuthority> authorities;
    private boolean enabled;

    public UserDetails(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.accountNonExpired = true;
        this.accountNonLocked = isNotLocked(user.getUserStatus());
        this.credentialsNonExpired = true;
        this.enabled = true;
        this.authorities = prepareCollection(user.getUserRole());
    }

    private Collection<GrantedAuthority> prepareCollection(UserRole userRole) {
        HashSet<GrantedAuthority> roles = new HashSet<>();
        if (userRole == UserRole.USER) {
            roles.add(new SimpleGrantedAuthority(UserRole.USER.name()));
        } else if (userRole == UserRole.MODERATOR){
            roles.add(new SimpleGrantedAuthority(UserRole.USER.name()));
            roles.add(new SimpleGrantedAuthority(UserRole.MODERATOR.name()));
        } else if (userRole == UserRole.ADMIN){
            roles.add(new SimpleGrantedAuthority(UserRole.USER.name()));
            roles.add(new SimpleGrantedAuthority(UserRole.MODERATOR.name()));
            roles.add(new SimpleGrantedAuthority(UserRole.ADMIN.name()));
        }
        return roles;
    }

    private static boolean isNotLocked(UserStatus status) {
        return status == UserStatus.ACTIVE || status == UserStatus.UNCONFIRMED;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
