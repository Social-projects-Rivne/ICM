package ua.softserve.rv_028.issuecitymonitor.controller;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.softserve.rv_028.issuecitymonitor.service.RestorePasswordService;

@RestController
@Log4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RestorePasswordController {

    RestorePasswordService restorePasswordService;

    @PostMapping(path = "/api/createResetToken")
    public void createResetToken(@RequestParam("email") String email) {
        log.debug("POST request for password reset token by email " + email);
        restorePasswordService.createResetToken(email);
    }

    @PostMapping(path = "/api/createNewPassword")
    public void createNewPassword(@RequestParam("email") String email, @RequestParam("password") String password,
                                  @RequestParam("token") String token) {
        log.debug("POST request for creating new password by email " + email);
        restorePasswordService.setNewPasswordForUser(email, password, token);
    }

}
