package ua.softserve.rv_028.issuecitymonitor.service;

import ua.softserve.rv_028.issuecitymonitor.dto.UserDto;

public interface RegistrationService {
    boolean isPossibleRegistration(String email);
    UserDto registrationUser(UserDto userDto);

}
