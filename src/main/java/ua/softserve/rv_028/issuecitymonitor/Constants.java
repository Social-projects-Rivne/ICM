package ua.softserve.rv_028.issuecitymonitor;

import java.text.SimpleDateFormat;

public final class Constants {

    public static final SimpleDateFormat DB_FORMAT = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
    public static final SimpleDateFormat JSON_FORMAT = new SimpleDateFormat("MM/dd/yyyy HH:mm");
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    public static final String REGISTRATION_FAIL_REASON = "Registration Failed! Most likely, a user with such email is already registered.";

}
