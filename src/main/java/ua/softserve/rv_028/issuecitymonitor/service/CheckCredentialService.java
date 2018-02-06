package ua.softserve.rv_028.issuecitymonitor.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ua.softserve.rv_028.issuecitymonitor.dao.UserDao;
import ua.softserve.rv_028.issuecitymonitor.entity.User;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.UserRole;
import ua.softserve.rv_028.issuecitymonitor.exception.ForbiddenException;

import java.util.Collection;

@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class CheckCredentialService {
    private final UserDao repository;

    public boolean checkCredential(long ownerId){
        User owner = repository.findOne(ownerId);
        Authentication userCredential = SecurityContextHolder.getContext().getAuthentication();
        System.err.println(userCredential.getName());
        System.err.println(userCredential.getAuthorities());
        if (owner.getUsername().equals(userCredential.getName()) || isAdmin(userCredential.getAuthorities())
            || isModerator(userCredential.getAuthorities())) {
            return true;
        } else {
            throw new ForbiddenException();
        }
    }

    private boolean isModerator(Collection<? extends GrantedAuthority> authorities) {
        return authorities.contains(UserRole.ADMIN);
    }

    private boolean isAdmin(Collection<? extends GrantedAuthority> authorities) {
        return authorities.contains(UserRole.ADMIN);
    }
}
