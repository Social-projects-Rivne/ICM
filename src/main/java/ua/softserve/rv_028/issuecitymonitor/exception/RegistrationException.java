package ua.softserve.rv_028.issuecitymonitor.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import ua.softserve.rv_028.issuecitymonitor.Constants;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RegistrationException extends RuntimeException{
    public RegistrationException() {
        super(Constants.REGISTRATION_FAIL_REASON);
    }
}
