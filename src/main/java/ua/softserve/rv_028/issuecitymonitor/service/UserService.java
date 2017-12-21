package ua.softserve.rv_028.issuecitymonitor.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ua.softserve.rv_028.issuecitymonitor.controller.AdviceController;
import ua.softserve.rv_028.issuecitymonitor.dao.UserDao;
import ua.softserve.rv_028.issuecitymonitor.dto.UserDto;
import ua.softserve.rv_028.issuecitymonitor.entity.User;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.UserRole;

import org.apache.log4j.Logger;
import ua.softserve.rv_028.issuecitymonitor.exception.UserNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Component
public class UserService {

    private static final Logger LOGGER = Logger.getLogger(AdviceController.class.getName());

    private final  UserDao userDao;
    private static boolean messages;
    @Autowired
    public UserService(UserDao userDao){
        this.userDao = userDao;
    }

    public static boolean isMessages() {
        return messages;
    }

    public void deleteById(long id) {
        User user = findOne(id);
        if(UserRole.ADMIN != user.getUserRole()){
            LOGGER.debug("User is deleted");
            userDao.delete(id);
        }else
            throw new UserNotFoundException("Users role is ADMIN, you can't delete ADMIN, try change his role!");
        LOGGER.debug("user role is " + user.getUserRole() + user.getIsDeleted());

    }



    private User findOne(long id){
        User user = userDao.findOne(id);
        LOGGER.debug("Find one " + user.toString());
        return user;
    }

    public List<UserDto> findAll(){
        List<UserDto> all = new ArrayList<>();
        for (User users : userDao.findAll()) {
            if(!users.getIsDeleted() && users.getUserRole() != null)
            all.add( new UserDto(users));
        }
        LOGGER.debug("Show all users!");
        return all;

    }

    public UserDto findByID(long id){
        User user = findOne(id);
        LOGGER.debug("User is finded by id");
        return new UserDto(user);
    }

    public UserDto updateUser(UserDto dto)  {
        User user = userDao.findOne(dto.getId());
        user.setUserRole(dto.getUserRole());
        user.setRegistrationDate(dto.getRegistrationDate());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPassword(dto.getPassword());
        user.setUserAgreement(dto.isUserAgreement());
        user.setUserStatus(dto.getUserStatus());
        user.setDeleteDate(dto.getDeleteDate());
        user.setAvatarUrl(dto.getAvatarUrl());

        LOGGER.debug("Adding user!" + user.toString());
        userDao.save(new User(dto));
        LOGGER.debug("Added user" + user.toString());
        LOGGER.debug(String.valueOf(userDao.countByUserRole(UserRole.ADMIN)));
        return new UserDto(user);
    }


}
