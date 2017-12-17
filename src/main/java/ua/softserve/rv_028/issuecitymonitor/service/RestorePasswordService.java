package ua.softserve.rv_028.issuecitymonitor.service;

import ua.softserve.rv_028.issuecitymonitor.dto.UserDto;

public interface RestorePasswordService {
    boolean createOrderRestorePassword(String email);
    void setNewPasswordForUser(UserDto user);
}
