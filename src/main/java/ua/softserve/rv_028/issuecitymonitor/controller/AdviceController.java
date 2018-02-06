package ua.softserve.rv_028.issuecitymonitor.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ua.softserve.rv_028.issuecitymonitor.exception.LastAdminException;
import ua.softserve.rv_028.issuecitymonitor.exception.ForbiddenException;
import ua.softserve.rv_028.issuecitymonitor.exception.RegistrationException;
import ua.softserve.rv_028.issuecitymonitor.exception.RestorePasswordException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ua.softserve.rv_028.issuecitymonitor.Constants.*;

@ControllerAdvice
@Log4j
public class AdviceController {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(IllegalArgumentException.class)
    public void handleIllegalArgumentException(Exception e) {
        log.debug(e.getMessage());
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = REGISTRATION_FAIL_REASON)
    @ExceptionHandler(RegistrationException.class)
    public void handleRegistrationException(RegistrationException e){
        log.debug(e.getMessage());
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = CHANGE_ROLE_FAIL)
    @ExceptionHandler(LastAdminException.class)
    public void editingError(LastAdminException e){
        log.error(e.getMessage());
    }

    @ResponseStatus(value = HttpStatus.FORBIDDEN, reason = FORBIDDEN_REASON)
    @ExceptionHandler(ForbiddenException.class)
    public void permissionRequest(ForbiddenException e){
        log.error(e.getMessage());
    }

    @ExceptionHandler(RestorePasswordException.class)
    public void handleRestorePasswordException(RestorePasswordException e, HttpServletResponse response) throws IOException {
        log.debug(e.getMessage());
        response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

}
