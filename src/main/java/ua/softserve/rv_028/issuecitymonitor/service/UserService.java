package ua.softserve.rv_028.issuecitymonitor.service;

import ua.softserve.rv_028.issuecitymonitor.dto.UserDto;
import ua.softserve.rv_028.issuecitymonitor.exception.UserNotFoundException;

import java.util.List;

public interface UserService {
    UserDto addUser(UserDto dto);
//    void deleteById(long id) throws UserNotFoundException;
    List<UserDto> findAll();
    UserDto findByID(long id) throws UserNotFoundException;
    UserDto updateUser(UserDto userDto) throws UserNotFoundException;
}
