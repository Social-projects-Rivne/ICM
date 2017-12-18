package ua.softserve.rv_028.issuecitymonitor.exception;

import ua.softserve.rv_028.issuecitymonitor.Constants;

public class RegistrationException extends RuntimeException{
    public RegistrationException() {
        super(Constants.REGISTRATION_FAIL_REASON);
    }
}
