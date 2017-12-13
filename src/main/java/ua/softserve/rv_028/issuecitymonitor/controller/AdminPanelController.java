package ua.softserve.rv_028.issuecitymonitor.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminPanelController {

    @GetMapping(value = "/api/admin_name")
    public String adminName(){
        Authentication userAuth = SecurityContextHolder.getContext().getAuthentication();
        return userAuth.getName();
    }
}

