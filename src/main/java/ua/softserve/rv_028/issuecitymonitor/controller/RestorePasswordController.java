package ua.softserve.rv_028.issuecitymonitor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.softserve.rv_028.issuecitymonitor.dto.UserDto;
import ua.softserve.rv_028.issuecitymonitor.service.RestorePasswordService;

@RestController
public class RestorePasswordController {

    private RestorePasswordService restorePasswordService;

    @Autowired
    public RestorePasswordController(RestorePasswordService restorePasswordService) {
        this.restorePasswordService = restorePasswordService;
    }

    @PostMapping(path = "/api/createOrderRestorePassword")
    public void createOrder(@RequestParam("email") String email){restorePasswordService.createOrderRestorePassword(email);}

    @PostMapping(path = "/api/createNewPassword")
    public void createNewPassword(@RequestParam("email") String email, @RequestParam("password") String password,
                                  @RequestParam("token") String token){
        restorePasswordService.setNewPasswordForUser(email, password, token);
    }

}
