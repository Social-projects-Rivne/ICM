package ua.softserve.rv_028.issuecitymonitor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.softserve.rv_028.issuecitymonitor.dto.UserDto;
import ua.softserve.rv_028.issuecitymonitor.service.RegistrationService;
import ua.softserve.rv_028.issuecitymonitor.service.RegistrationServiceImpl;

@RestController
public class RegistrationController {

    private final RegistrationService service;

    @Autowired
    public RegistrationController(RegistrationService service) {
        this.service = service;
    }

    @PostMapping(path = "/api/checkEmail")
    public Boolean checkEmail(@RequestParam("email") String email){
        return service.isUserExist(email);
    }

    @PostMapping(path = "/api/registration")
    public HttpStatus userRegistration(@RequestBody UserDto user) {

        //TODO Throw exception, change boolean to void
        if (!someFieldIsNull(user) && !someFieldIsEmpty(user))
            service.registrationUser(user);
        else
            return HttpStatus.BAD_REQUEST;

        return HttpStatus.CREATED;
    }
    //TODO Move logic to service
    private boolean someFieldIsEmpty(UserDto dto){
        return (dto.getFirstName().isEmpty() || dto.getLastName().isEmpty()) ||
                (dto.getEmail().isEmpty() || dto.getPassword().isEmpty());
    }

    private boolean someFieldIsNull(UserDto dto){
        return (dto.getEmail() == null && dto.getPassword() == null) &&
                (dto.getFirstName() == null && dto.getLastName() == null);
    }


}
