package ua.softserve.rv_028.issuecitymonitor.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ua.softserve.rv_028.issuecitymonitor.dao.UserDao;
import ua.softserve.rv_028.issuecitymonitor.dto.UserDto;
import ua.softserve.rv_028.issuecitymonitor.entity.User;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.UserRole;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.UserStatus;
import ua.softserve.rv_028.issuecitymonitor.exception.RegistrationException;
import ua.softserve.rv_028.issuecitymonitor.service.mappers.UserMapper;

@Service
@Log4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegistrationService {

    UserDao userDao;
    EmailService emailService;
    UserMapper userMapper;
    BCryptPasswordEncoder passwordEncoder;

    public boolean isPossibleRegistration(String email) {
        User user = userDao.findUserByUsername(email);
        return isUserExist(email) && (user.getUserStatus() != UserStatus.DELETED);
    }

    public UserDto registrationUser(UserDto dto) {
        if (isUserExist(dto.getEmail()))
            throw new RegistrationException();

        try {
            User user = new User();
            user.setFirstName(dto.getFirstName());
            user.setLastName(dto.getLastName());
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
            user.setUsername(dto.getEmail());
            user.setUserStatus(UserStatus.UNCONFIRMED);
            user.setUserRole(UserRole.USER);

            user = userDao.save(user);

            log.info("The user " + dto.getEmail() + " has been registered");
            emailService.sendGreetingEmail(dto.getEmail(), dto.getFirstName(), dto.getLastName());
            return userMapper.toDto(user);
        } catch (RuntimeException e){
            throw new RegistrationException();
        }
    }

    private boolean isUserExist(String email){
        User user = userDao.findUserByUsername(email);
        return user != null && user.getUsername().equals(email);
    }
}
