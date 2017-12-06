package ua.softserve.rv_028.issuecitymonitor.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminPanelController {

    @RequestMapping(value = "/api/admin_name", method = RequestMethod.GET)
    public ResponseEntity<String> adminName(){
        Authentication userAuth = SecurityContextHolder.getContext().getAuthentication();
        return new ResponseEntity<String>(userAuth.getName(), HttpStatus.OK);
    }
}
