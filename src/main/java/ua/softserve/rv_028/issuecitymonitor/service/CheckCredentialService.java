package ua.softserve.rv_028.issuecitymonitor.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ua.softserve.rv_028.issuecitymonitor.dao.UserDao;
import ua.softserve.rv_028.issuecitymonitor.entity.User;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.UserRole;
import ua.softserve.rv_028.issuecitymonitor.exception.ForbiddenException;

@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class CheckCredentialService {
    private final UserDao repository;

    public boolean checkCredential(long ownerId){
        User owner = repository.findOne(ownerId);
        Authentication webUserAuthentication = SecurityContextHolder.getContext().getAuthentication();
        User webUser = repository.findUserByUsername(webUserAuthentication.getName());
        if (owner.getUsername().equals(webUserAuthentication.getName()) || is(webUser, UserRole.ADMIN)
                || is(webUser, UserRole.MODERATOR)) {
            return true;
        } else {
            throw new ForbiddenException();
        }
    }

    private boolean is(User moderator, UserRole role) {
        return moderator.getUserRole().is(role);
    }
}
