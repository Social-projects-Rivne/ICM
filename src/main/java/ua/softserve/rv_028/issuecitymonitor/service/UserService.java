package ua.softserve.rv_028.issuecitymonitor.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ua.softserve.rv_028.issuecitymonitor.dao.UserDao;
import ua.softserve.rv_028.issuecitymonitor.dto.UserDto;
import ua.softserve.rv_028.issuecitymonitor.entity.User;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.UserRole;
import ua.softserve.rv_028.issuecitymonitor.exception.LastAdminException;
import ua.softserve.rv_028.issuecitymonitor.service.mappers.UserMapper;

@Service
@Log4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserService {

    UserMapper userMapper;

    UserDao userDao;

    public Page<UserDto> findAllByPage(int pageNumber, int pageSize, Sort.Direction direction, String columns,
                                       boolean isDeleted) {
        String[] columnArray = columns.split(",");
        PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize, direction, columnArray);
        Page<User> users = userDao.findAll(isDeleted, pageRequest);
        log.debug("Found all users");
        return userMapper.toDtoPage(users);
    }

    public UserDto findById(long id){
        User user = findOne(id);
        log.debug("Found " + user);
        return userMapper.toDto(user);
    }

    public UserDto update(UserDto userDto)  {
        User user = findOne(userDto.getId());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setUserStatus(userDto.getUserStatus());
        log.debug("Count of Admins in DB" + userDao.countAdmins());
        if (userDao.countAdmins() <= 1 && (user.getUserRole() == UserRole.ADMIN) && (userDto.getUserRole() != UserRole.ADMIN)) {
            throw new LastAdminException();
        } else {
            user.setUserRole(userDto.getUserRole());
        }
        user = userDao.save(user);
        log.debug("Updated " + user.toString());
        return userMapper.toDto(user);
    }

    public void deleteById(long id) {
        if (userDao.countAdmins() <= 1 && (findOne(id).getUserRole() == UserRole.ADMIN)) {
            throw new LastAdminException();
        } else {
            userDao.delete(id);
            log.debug("Deleted user " + id);
        }
    }

    private User findOne(long id){
        User user = userDao.findOne(id);
        if(user == null) {
            throw new IllegalStateException("user id not found: " + id);
        }
        return user;
    }

}
