package ua.softserve.rv_028.issuecitymonitor.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message){
        super(message);
    }
}
