package ua.softserve.rv_028.issuecitymonitor.service;

import ua.softserve.rv_028.issuecitymonitor.dto.UserDto;

public interface RegistrationService {
    boolean isUserExist(String email);
    void registrationUser(UserDto userDto);

}
