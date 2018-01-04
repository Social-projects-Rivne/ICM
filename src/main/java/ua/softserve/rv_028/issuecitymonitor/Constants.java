package ua.softserve.rv_028.issuecitymonitor;

import java.time.format.DateTimeFormatter;

public final class Constants {
    public static final int PAGE_SIZE = 10;
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    public static final String REGISTRATION_FAIL_REASON = "Registration Failed! Most likely, a user with such email is already registered.";
    public static final String CHANGE_ROLE_FAIL = "You can't decrease role for LAST ADMIN!";
}
