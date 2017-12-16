package ua.softserve.rv_028.issuecitymonitor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.softserve.rv_028.issuecitymonitor.service.RestorePasswordService;

@RestController
public class RestorePasswordController {

    private RestorePasswordService restorePasswordService;

    @Autowired
    public RestorePasswordController(RestorePasswordService restorePasswordService) {
        this.restorePasswordService = restorePasswordService;
    }

    @PostMapping(path = "/api/restorePassword")
    public void restore(@RequestParam("email") String email){restorePasswordService.createOrderRestorePassword(email);}
}
