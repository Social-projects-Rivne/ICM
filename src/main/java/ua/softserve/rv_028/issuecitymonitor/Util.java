package ua.softserve.rv_028.issuecitymonitor;

import java.util.Date;

import static ua.softserve.rv_028.issuecitymonitor.Constants.DATE_FORMAT;

public class Util {
    public static String currentDate(){
        return DATE_FORMAT.format(new Date());
    }
}
