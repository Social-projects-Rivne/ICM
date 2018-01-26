package ua.softserve.rv_028.issuecitymonitor.controller.PDF;

import ua.softserve.rv_028.issuecitymonitor.entity.User;

import java.time.LocalDateTime;

public interface PdfWritable {
    Long getId();

    String getTitle();

    String getDescription();

    String getCat();

    User getUserDto();

    LocalDateTime getInitialDate();

    String getMail();
}
