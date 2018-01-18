package ua.softserve.rv_028.issuecitymonitor.controller;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.softserve.rv_028.issuecitymonitor.dto.UserDto;
import ua.softserve.rv_028.issuecitymonitor.service.RegistrationService;

@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class RegistrationController {

    RegistrationService service;

    @PostMapping(path = "/api/checkEmail")
    public Boolean checkEmail(@RequestParam("email") String email){
        return service.isPossibleRegistration(email);
    }

    @PostMapping(path = "/api/registration")
    public void userRegistration(@RequestBody UserDto user) {
        service.registrationUser(user);
    }
}
