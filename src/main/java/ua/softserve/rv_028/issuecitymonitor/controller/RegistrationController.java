package ua.softserve.rv_028.issuecitymonitor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.softserve.rv_028.issuecitymonitor.dto.UserDto;
import ua.softserve.rv_028.issuecitymonitor.service.RegistrationServiceImpl;

@RestController
public class RegistrationController {

    @Autowired
    private RegistrationServiceImpl service;

    @PostMapping(path = "/api/checkEmail")
    public Boolean checkEmail(@RequestParam("email") String email){
        return service.isUserExist(email);
    }

    @PostMapping(path = "/api/registration")
    public void userRegistration(@RequestBody UserDto user) {
        service.registrationUser(user);
    }
}
