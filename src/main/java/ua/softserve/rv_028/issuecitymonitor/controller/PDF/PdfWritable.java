package ua.softserve.rv_028.issuecitymonitor.controller.PDF;

import ua.softserve.rv_028.issuecitymonitor.entity.User;

import java.time.LocalDateTime;

public interface PdfWritable {
    default Long getId(){return null;}

    default String getTitle() {
        return null;
    }

    default String getDescription() {
        return null;
    }

    default LocalDateTime getInitialDate() {
        return null;
    }

    default String getRole(){
        return null;
    }

    default String getCat() {
        return null;
    }

    default User getUserDto() {
        return null;
    }

    default String getMail() {
        return null;
    }

    default String getPhone() {
        return null;
    }

    default LocalDateTime getRegDate() {
        return null;
    }
}
