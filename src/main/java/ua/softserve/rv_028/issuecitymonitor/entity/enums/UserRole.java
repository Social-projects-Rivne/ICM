package ua.softserve.rv_028.issuecitymonitor.entity.enums;

import org.springframework.security.core.GrantedAuthority;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public enum UserRole implements GrantedAuthority{
    ADMIN,
    MODERATOR,
    USER;

    @Override
    public String getAuthority() {
        return this.name();
    }

    public static Collection<GrantedAuthority> collectionForRole(UserRole userRole){
        if (userRole == ADMIN)
            return Arrays.asList(USER, MODERATOR, ADMIN);
        else if (userRole == MODERATOR)
            return Arrays.asList(USER, MODERATOR);
        else
            return Collections.singletonList(USER);
    }

    public boolean is(UserRole role){
        return this.equals(role);
    }

}