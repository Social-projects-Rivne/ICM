package ua.softserve.rv_028.issuecitymonitor.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserve.rv_028.issuecitymonitor.dao.UserDao;
import ua.softserve.rv_028.issuecitymonitor.dto.UserDto;
import ua.softserve.rv_028.issuecitymonitor.entity.User;
import ua.softserve.rv_028.issuecitymonitor.exception.LastAdminException;
import ua.softserve.rv_028.issuecitymonitor.service.mappers.UserMapper;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private UserMapper mapper;

    private static final Logger LOGGER = Logger.getLogger(UserService.class);

    private final  UserDao userDao;

    @Autowired
    public UserService(UserDao userDao, UserMapper mapper){
        this.userDao = userDao;
        this.mapper = mapper;
    }

    public void deleteById(long id) {
        userDao.delete(id);
        LOGGER.debug("Deleted user " + id);
    }

    private User findOne(long id){
        User user = userDao.findOne(id);
        if(user == null) {
            throw new IllegalStateException("user id not found: " + id);
        }
        return user;
    }

    public List<UserDto> findAll(){
        List<UserDto> all = new ArrayList<>();
        for (User user : userDao.findAllByOrderByIdAsc()) {
            all.add(mapper.toDto(user));
        }
        LOGGER.debug("Found all users");
        return all;

    }

    public UserDto findByID(long id){
        User user = findOne(id);
        LOGGER.debug("Found " + user);
        return mapper.toDto(user);
    }

    public UserDto updateUser(UserDto userDto)  {
        User user = findOne(userDto.getId());
        user.setUserRole(userDto.getUserRole());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setUserStatus(userDto.getUserStatus());
        LOGGER.debug("Count of Admins in DB " + userDao.countAdmins());
        if (userDao.countAdmins() > 1) {
            userDao.save(user);
            return mapper.toDto(user);
        }
        else{
            throw new LastAdminException();
        }
    }


}
