package ua.softserve.rv_028.issuecitymonitor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.softserve.rv_028.issuecitymonitor.dto.UserDto;
import ua.softserve.rv_028.issuecitymonitor.service.RegistrationService;
import ua.softserve.rv_028.issuecitymonitor.service.RegistrationServiceImpl;

@RestController
public class RegistrationController {

    @Autowired
    RegistrationService service;

    @PostMapping(path = "/api/checkEmail")
    public Boolean checkEmail(@RequestParam("email") String email){
        System.out.println(email);
        return service.isUserExist(email);
    }

    @PostMapping(path = "/api/registration")
    public Boolean userRegistration(@RequestBody UserDto user) {
        if (!someFieldIsNull(user)) {
            if (!someFieldIsEmpty(user)) {
                try {
                    service.registrationUser(user);
                    return true;
                } catch (RuntimeException exception) {
                    return false;
                }
            }
        }
        return false;
    }

    private boolean someFieldIsEmpty(UserDto dto){
        return (dto.getFirstName().isEmpty() || dto.getLastName().isEmpty()) ||
                (dto.getEmail().isEmpty() || dto.getPassword().isEmpty());
    }

    private boolean someFieldIsNull(UserDto dto){
        return (dto.getEmail() == null && dto.getPassword() == null) &&
                (dto.getFirstName() == null && dto.getLastName() == null);
    }


}
