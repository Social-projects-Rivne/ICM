package ua.softserve.rv_028.issuecitymonitor.service;

import ua.softserve.rv_028.issuecitymonitor.dto.UserDto;

public interface EmailService {
    void sendGreetingEmail(String emailUser, String firstName, String lastName);
    void sendRestorePasswordEmail(UserDto user, String token);
    void sendEmail(UserDto user, String subject, String text);

}
