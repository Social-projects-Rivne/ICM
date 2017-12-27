package ua.softserve.rv_028.issuecitymonitor.service;
import org.springframework.beans.factory.annotation.Autowired;
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
        for (User users : userDao.findAllByOrderByIdAsc()) {
            if(!users.getIsDeleted())
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

    public UserDto updateUser(UserDto userDto)  {
        User user = findOne(userDto.getId());
        user.setUserRole(userDto.getUserRole());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setUserStatus(userDto.getUserStatus());
        LOGGER.debug("Count of Admins in DB" + userDao.countAdmins());
        if (userDao.countAdmins() > 1) {
            userDao.save(user);
            return new UserDto(user);
        }
        else{
            throw new LastAdminException();
        }

    }


}
