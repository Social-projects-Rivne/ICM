package ua.softserve.rv_028.issuecitymonitor.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import ua.softserve.rv_028.issuecitymonitor.service.RestorePasswordService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class RestorePasswordController {

    private static final Logger LOGGER = Logger.getLogger(RestorePasswordController.class);

    private RestorePasswordService restorePasswordService;

    @Autowired
    public RestorePasswordController(RestorePasswordService restorePasswordService) {
        this.restorePasswordService = restorePasswordService;
    }

    @PostMapping(path = "/api/createResetToken")
    public void createResetToken(@RequestParam("email") String email) {
        LOGGER.debug("POST request for password reset token by email " + email);
        restorePasswordService.createResetToken(email);
    }

    @PostMapping(path = "/api/createNewPassword")
    public void createNewPassword(@RequestParam("email") String email, @RequestParam("password") String password,
                                  @RequestParam("token") String token) {
        LOGGER.debug("POST request for creating new password by email " + email);
        restorePasswordService.setNewPasswordForUser(email, password, token);
    }

    @GetMapping(path = "/restore/password/{token}")
    public ModelAndView resetPassword(@PathVariable(name = "token") String token, RedirectAttributes redirect) {
        LOGGER.debug("GET request for creating new password");
        restorePasswordService.checkTokenIsExist(token);
        redirect.addAttribute("token", token);
        return new ModelAndView("redirect:/restore-password");
    }

    @PostMapping(path = "/api/createNewPassword")
    public void restorePassword(@RequestParam("password") String password,
                                  @RequestParam("token") String token) {
        LOGGER.debug("POST request for creating new password");
        restorePasswordService.restorePassword(token, password);
    }

}
