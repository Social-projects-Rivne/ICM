package ua.softserve.rv_028.issuecitymonitor;

import java.text.SimpleDateFormat;
import java.util.Date;

import static ua.softserve.rv_028.issuecitymonitor.Constants.DATE_FORMAT;

public class Util {
    public static String currentDate(){
        return new SimpleDateFormat("dd/MM/yyyy").format(new Date());
    }
}
