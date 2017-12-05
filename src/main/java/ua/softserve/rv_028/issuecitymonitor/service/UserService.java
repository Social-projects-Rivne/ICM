package ua.softserve.rv_028.issuecitymonitor.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.softserve.rv_028.issuecitymonitor.dao.UserDao;
import ua.softserve.rv_028.issuecitymonitor.dto.UserDto;
import ua.softserve.rv_028.issuecitymonitor.entity.User;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserService {

    @Autowired
    private UserDao userDao;

    public UserDto addUser(UserDto dto){
        userDao.save(new User(dto));
        return dto;
    }

    public List<UserDto> findAll(){
        List<User> all = userDao.findAll();
        return all.stream().map(UserDto::new).collect(Collectors.toList());
    }
}
