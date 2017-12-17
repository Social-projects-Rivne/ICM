package ua.softserve.rv_028.issuecitymonitor.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ua.softserve.rv_028.issuecitymonitor.Util;
import ua.softserve.rv_028.issuecitymonitor.controller.AdviceController;
import ua.softserve.rv_028.issuecitymonitor.dao.RestorePasswordDao;
import ua.softserve.rv_028.issuecitymonitor.dao.UserDao;
import ua.softserve.rv_028.issuecitymonitor.dto.UserDto;
import ua.softserve.rv_028.issuecitymonitor.entity.RestorePassword;
import ua.softserve.rv_028.issuecitymonitor.entity.User;
import ua.softserve.rv_028.issuecitymonitor.exception.RegistrationException;

import java.util.UUID;

@Service
public class RestorePasswordServiceImpl implements RestorePasswordService {

    private static final Logger LOGGER = Logger.getLogger(AdviceController.class.getName());

    private UserDao userDao;
    private RestorePasswordDao restorePasswordDao;
    private EmailService emailService;
    private MapperService mapperService;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public RestorePasswordServiceImpl(UserDao userDao, RestorePasswordDao restorePasswordDao, EmailService emailService,
                                      MapperService mapperService, BCryptPasswordEncoder encoder) {
        this.userDao = userDao;
        this.restorePasswordDao = restorePasswordDao;
        this.emailService = emailService;
        this.mapperService = mapperService;
        this.passwordEncoder = encoder;
    }

    @Override
    public boolean createOrderRestorePassword(String email) {
        User user = userDao.findUserByUsername(email);
        if (user == null)
            return false;

        if (user.getUsername().equals(email)){
            String token = UUID.randomUUID().toString();
            restorePasswordDao.save(new RestorePassword(
                    user,
                    token,
                    Util.currentDate()));
            emailService.sendRestorePasswordEmail(mapperService.fromEntityToDto(user), token);
            return true;
        } else {
            return false;
        }
    }


    @Override
    public void setNewPasswordForUser(UserDto userDto) {
        User userEntity = userDao.findUserByUsername(userDto.getEmail());

        if (userEntity == null)
            throw new IllegalStateException("User with the following email \'" + userDto.getEmail() + "\' doesn't not exist");

        if (userDto.getPassword() == null || userDto.getPassword().isEmpty())
            throw new IllegalArgumentException("User \'" + userDto.getEmail() + "\' wrote empty password");

        userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userDao.save(userEntity);
        LOGGER.info("User \'" + userDto.getEmail() + "\' have been update his password");
    }
}
