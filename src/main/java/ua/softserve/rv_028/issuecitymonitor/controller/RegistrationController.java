package ua.softserve.rv_028.issuecitymonitor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.softserve.rv_028.issuecitymonitor.service.RegistrationService;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
public class RegistrationController {

    @Autowired
    RegistrationService service;

    @PostMapping(path = "/api/checkEmail")
    public Boolean checkEmail(@RequestParam("email") String email){
        System.out.println(email);
        return service.existUser(email);
    }

}
