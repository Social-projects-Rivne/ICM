package ua.softserve.rv_028.issuecitymonitor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.softserve.rv_028.issuecitymonitor.dto.RegistrationDto;
import ua.softserve.rv_028.issuecitymonitor.service.RegistrationService;

@RestController
public class RegistrationController {

    @Autowired
    RegistrationService service;

    @PostMapping(path = "/api/checkEmail")
    public Boolean checkEmail(@RequestParam("email") String email){
        System.out.println(email);
        return service.existUser(email);
    }

    @PostMapping(path = "/api/registration")
    public Boolean userRegistration(@RequestBody RegistrationDto user) {
        if (!someFieldIsNull(user)) {
            if (!someFieldIsEmpty(user)) {
                try {
                    service.userRegistration(user.toEntity());
                } catch (RuntimeException exception) {
                    System.out.println("Hopa");
                    System.out.println(exception.getMessage());
                    return false;
                }
            }
        }

        System.out.println("The end");
        return true;
    }

    private boolean someFieldIsEmpty(RegistrationDto dto){
        return (dto.getFirstName().isEmpty() || dto.getLastName().isEmpty()) ||
                (dto.getEmail().isEmpty() || dto.getPassword().isEmpty());
    }

    private boolean someFieldIsNull(RegistrationDto dto){
        return (dto.getEmail() == null && dto.getPassword() == null) &&
                (dto.getFirstName() == null && dto.getLastName() == null);
    }


}
