package ua.softserve.rv_028.issuecitymonitor.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.softserve.rv_028.issuecitymonitor.dao.UserDao;
import ua.softserve.rv_028.issuecitymonitor.dto.UserDto;
import ua.softserve.rv_028.issuecitymonitor.entity.User;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.UserRole;
import ua.softserve.rv_028.issuecitymonitor.exception.UserNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Component
public class UserService {
    private static final Logger LOGGER = Logger.getLogger(UserService.class.getName());
    private final  UserDao userDao;
    private static boolean messages;

    @Autowired
    public UserService(UserDao userDao){
        this.userDao = userDao;
    }

    public static boolean isMessages() {
        return messages;
    }

    public void deleteById(long id) throws UserNotFoundException {
        User user = findOne(id);
        if(UserRole.ADMIN != user.getUserRole()){
            LOGGER.info("User is deleted");
            userDao.delete(id);
            messages = true;
            UserDto.setCount(UserDto.getCount() -1);
        }else
            messages = false;
        LOGGER.info("user role is " + user.getUserRole() + user.getIsDeleted());

    }



    private User findOne(long id) throws UserNotFoundException {
        User user = userDao.findOne(id);
        if (user == null){
            throw new UserNotFoundException("User not found");
        }
        return user;
    }

    public List<UserDto> findAll(){
        List<UserDto> all = new ArrayList<>();
        for (User users : userDao.findAll()) {
            if(!users.getIsDeleted())
            all.add( new UserDto(users));
        }
        LOGGER.info("Show all users!");
        return all;

    }

    public UserDto findByID(long id) throws UserNotFoundException {
        User user = findOne(id);
        LOGGER.info("User is finded by id");
        return new UserDto(user);
    }

    public UserDto updateUser(UserDto dto)  {
        User user = userDao.findOne(dto.getId());
        user.setUserRole(dto.getUserRole());
        user.setRegistrationDate(dto.getRegistrationDate());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPassword(dto.getPassword());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setUserAgreement(dto.isUserAgreement());
        user.setUserStatus(dto.getUserStatus());
        user.setDeleteDate(dto.getDeleteDate());
        user.setAvatarUrl(dto.getAvatarUrl());

        LOGGER.info("Adding user!");

        userDao.save(new User(dto));

        LOGGER.info("Added user");
        return new UserDto(user);


    }


}
