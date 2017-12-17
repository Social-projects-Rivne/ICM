package ua.softserve.rv_028.issuecitymonitor.service;

import ua.softserve.rv_028.issuecitymonitor.dto.UserDto;
import ua.softserve.rv_028.issuecitymonitor.entity.User;

public interface RestorePasswordService {
    boolean createOrderRestorePassword(String email);
    User setNewPasswordForUser(UserDto user);
}
