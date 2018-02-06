package ua.softserve.rv_028.issuecitymonitor.exception;

import static ua.softserve.rv_028.issuecitymonitor.Constants.FORBIDDEN_REASON;

public class ForbiddenException extends RuntimeException {
    public ForbiddenException(){super(FORBIDDEN_REASON);}
}
