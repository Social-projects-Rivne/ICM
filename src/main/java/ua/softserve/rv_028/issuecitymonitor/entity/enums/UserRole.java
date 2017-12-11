package ua.softserve.rv_028.issuecitymonitor.entity.enums;


import org.springframework.security.core.GrantedAuthority;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public enum UserRole implements GrantedAuthority{
    ADMIN ,
    MODERATOR,
    USER;

    UserRole(){

    }

    @Override
    public String getAuthority() {
        return this.name();
    }

    public static Collection<GrantedAuthority> collectionForRole(UserRole role){
        if (role == ADMIN)
            return Arrays.asList(USER, MODERATOR, ADMIN);
        else if (role == MODERATOR)
            return Arrays.asList(USER, MODERATOR);
        else
            return Collections.singletonList(USER);
    }

}

