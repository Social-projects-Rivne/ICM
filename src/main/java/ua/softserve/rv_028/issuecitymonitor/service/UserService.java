package ua.softserve.rv_028.issuecitymonitor.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import ua.softserve.rv_028.issuecitymonitor.controller.AdviceController;
import ua.softserve.rv_028.issuecitymonitor.dao.UserDao;
import ua.softserve.rv_028.issuecitymonitor.dto.UserDto;
import ua.softserve.rv_028.issuecitymonitor.entity.User;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.UserRole;

import org.apache.log4j.Logger;
import ua.softserve.rv_028.issuecitymonitor.exception.LastAdminException;
import ua.softserve.rv_028.issuecitymonitor.exception.UserNotFoundException;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserService {

    private MapperService mapper;

    private static final Logger LOGGER = Logger.getLogger(AdviceController.class.getName());

    private final  UserDao userDao;

    @Autowired
    public UserService(UserDao userDao, MapperService mapper){
        this.userDao = userDao;
        this.mapper = mapper;
    }

    public void deleteById(long id) {
        userDao.delete(id);
        LOGGER.debug("Deleted user " + id);
    }

    private User findOne(long id){
        User user = userDao.findOne(id);
        LOGGER.debug("Find one " + user.toString());
        return user;
    }

    public Page<UserDto> findAllByPage(Pageable pageable) {
        Page<User> users = userDao.findAll(pageable);
        return users.map(mapper::fromEntityToDto);
    }

    public UserDto findByID(long id){
        User user = findOne(id);
        LOGGER.debug("User is finded by id");
        return mapper.fromEntityToDto(user);
    }

    public UserDto updateUser(UserDto userDto)  {
        User user = findOne(userDto.getId());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setUserStatus(userDto.getUserStatus());
        LOGGER.debug("Count of Admins in DB" + userDao.countAdmins());
        if (userDao.countAdmins() <= 1 && (user.getUserRole() == UserRole.ADMIN) && (userDto.getUserRole() != UserRole.ADMIN)) {
            throw new LastAdminException();
        } else {
            user.setUserRole(userDto.getUserRole());
        }

        return mapper.fromEntityToDto(userDao.save(user));
    }

}
