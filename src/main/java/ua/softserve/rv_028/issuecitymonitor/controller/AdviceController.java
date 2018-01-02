package ua.softserve.rv_028.issuecitymonitor.controller;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ua.softserve.rv_028.issuecitymonitor.Constants;
import ua.softserve.rv_028.issuecitymonitor.exception.LastAdminException;
import ua.softserve.rv_028.issuecitymonitor.exception.RegistrationException;
import ua.softserve.rv_028.issuecitymonitor.exception.RestorePasswordException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class AdviceController {

    private static final Logger LOGGER = Logger.getLogger(AdviceController.class);

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(IllegalArgumentException.class)
    public void handleIllegalArgumentException(Exception e) {
        LOGGER.debug(e.getMessage());
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = Constants.REGISTRATION_FAIL_REASON)
    @ExceptionHandler(RegistrationException.class)
    public void handleRegistrationException(RegistrationException e){
        LOGGER.debug(e.getMessage());
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = Constants.CHANGE_ROLE_FAIL)
    @ExceptionHandler(LastAdminException.class)
    public void editingError(LastAdminException e){
        LOGGER.error(e.getMessage());
    }

    @ExceptionHandler(RestorePasswordException.class)
    public void handleRestorePasswordException(RestorePasswordException e, HttpServletResponse response) throws IOException {
        LOGGER.debug(e.getMessage());
        response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

}
