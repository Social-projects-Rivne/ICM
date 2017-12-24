package ua.softserve.rv_028.issuecitymonitor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.softserve.rv_028.issuecitymonitor.dto.UserDto;
import ua.softserve.rv_028.issuecitymonitor.entity.User;
import ua.softserve.rv_028.issuecitymonitor.service.MapperService;

import java.util.Collection;

@RestController
public class AuthenticatedUsersController {

    @Autowired
    private MapperService mapperService;

    @GetMapping(value = "/api/user-name")
    public String adminName(){
        Authentication userAuth = SecurityContextHolder.getContext().getAuthentication();
        return userAuth.getName();
    }

    @GetMapping(value = "/api/authority")
    public Collection<? extends GrantedAuthority> authority(){
        Authentication userAuth = SecurityContextHolder.getContext().getAuthentication();
        return userAuth.getAuthorities();
    }

    @GetMapping(value = "/api/details")
    public UserDto userDetails(){
        Authentication userAuth = SecurityContextHolder.getContext().getAuthentication();
        if (!userAuth.getName().equals("anonymousUser"))
            return mapperService.fromEntityToDto((User) userAuth.getPrincipal());
        else
            return new UserDto();
    }
}

