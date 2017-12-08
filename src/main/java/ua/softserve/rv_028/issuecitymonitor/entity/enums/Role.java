package ua.softserve.rv_028.issuecitymonitor.entity.enums;

import org.springframework.security.core.GrantedAuthority;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public enum Role implements GrantedAuthority{
    ADMIN ,
    MODERATOR,
    USER;

    Role(){

    }

    @Override
    public String getAuthority() {
        return this.name();
    }

    public static Collection<GrantedAuthority> collectionForRole(Role role){
        if (role == ADMIN)
            return Arrays.asList(USER, MODERATOR, ADMIN);
        else if (role == MODERATOR)
            return Arrays.asList(USER, MODERATOR);
        else
            return Collections.singletonList(USER);
    }
}

