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
public class UserServiceImpl implements UserService{
    private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class.getName());
    private final  UserDao userDao;
    private static boolean messages;

    @Autowired
    public UserServiceImpl(UserDao userDao){
        this.userDao = userDao;
    }

    public static boolean isMessages() {
        return messages;
    }

    @Override
    public UserDto addUser(UserDto dto){
        User user = new User();
        user.setId(dto.getId());
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

    @Override
    public void deleteById(long id) throws UserNotFoundException {
        User user = findOne(id);
        if(UserRole.ADMIN != user.getUserRole()){
            LOGGER.info("User is deleted");
            user.delete();
            messages = true;
            UserDto.setCount(UserDto.getCount() -1);
        }else
            messages = false;
        LOGGER.info("user role is " + user.getUserRole());

    }



    private User findOne(long id) throws UserNotFoundException {
        User user = userDao.findOne(id);
        if (user == null){
            throw new UserNotFoundException("User not found");
        }
        return user;
    }

    @Override
    public List<UserDto> findAll(){
        List<UserDto> all = new ArrayList<>();
        for (User users : userDao.findAll()) {
            all.add( new UserDto(users));
        }
        LOGGER.info("Show all users!");
        return all;

    }

    @Override
    public UserDto findByID(long id) throws UserNotFoundException {
        User user = findOne(id);
        LOGGER.info("User is finded");
        return new UserDto(user);
    }

    @Override
    public UserDto updateUser(UserDto dto) throws UserNotFoundException {
        User user = userDao.findOne(dto.getId());
        user.setId(dto.getId());
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
