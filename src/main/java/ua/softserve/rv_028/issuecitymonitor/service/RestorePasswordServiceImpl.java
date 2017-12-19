package ua.softserve.rv_028.issuecitymonitor.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ua.softserve.rv_028.issuecitymonitor.Util;
import ua.softserve.rv_028.issuecitymonitor.controller.AdviceController;
import ua.softserve.rv_028.issuecitymonitor.dao.RestorePasswordDao;
import ua.softserve.rv_028.issuecitymonitor.dao.UserDao;
import ua.softserve.rv_028.issuecitymonitor.entity.RestorePassword;
import ua.softserve.rv_028.issuecitymonitor.entity.User;
import ua.softserve.rv_028.issuecitymonitor.exception.RestorePasswordException;

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
            throw new RestorePasswordException("User with the following email \'" + email + "\' doesn't not exist");

        if (user.getUsername().equals(email)){
            String token = UUID.randomUUID().toString();
            restorePasswordDao.deleteByUser(user);
            restorePasswordDao.save(new RestorePassword(
                    user,
                    token,
                    Util.currentDate()));
            emailService.sendRestorePasswordEmail(mapperService.toDto(user), token);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void setNewPasswordForUser(String email, String password, String token) {
        User userEntity = userDao.findUserByUsername(email);

        if (userEntity == null)
            throw new RestorePasswordException("User with the following email \'" + email + "\' doesn't not exist");

        if (password == null || password.isEmpty())
            throw new RestorePasswordException("User \'" + email + "\' wrote empty password");

        if (token == null || token.isEmpty())
            throw new RestorePasswordException("User \'" + email + "\' wrote empty token");

        if (!restorePasswordDao.findByUser(userEntity).getToken().equals(token))
            throw new RestorePasswordException("User \'" + email + "\' wrote incorrect token");

        userEntity.setPassword(passwordEncoder.encode(password));
        userDao.save(userEntity);
        LOGGER.info("User \'" + email + "\' have been update his password");
    }
}
