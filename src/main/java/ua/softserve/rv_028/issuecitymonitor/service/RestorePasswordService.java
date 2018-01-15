package ua.softserve.rv_028.issuecitymonitor.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ua.softserve.rv_028.issuecitymonitor.dao.RestorePasswordDao;
import ua.softserve.rv_028.issuecitymonitor.dao.UserDao;
import ua.softserve.rv_028.issuecitymonitor.entity.RestorePassword;
import ua.softserve.rv_028.issuecitymonitor.entity.User;
import ua.softserve.rv_028.issuecitymonitor.exception.RestorePasswordException;
import ua.softserve.rv_028.issuecitymonitor.service.mappers.UserMapper;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class RestorePasswordService  {

    private static final Logger LOGGER = Logger.getLogger(RestorePasswordService.class);

    private UserDao userDao;
    private RestorePasswordDao restorePasswordDao;
    private EmailService emailService;
    private UserMapper userMapper;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public RestorePasswordService(UserDao userDao, RestorePasswordDao restorePasswordDao, EmailService emailService,
                                      UserMapper userMapper, BCryptPasswordEncoder encoder) {
        this.userDao = userDao;
        this.restorePasswordDao = restorePasswordDao;
        this.emailService = emailService;
        this.userMapper = userMapper;
        this.passwordEncoder = encoder;
    }

    public void createResetToken(String email) {
        User user = userDao.findUserByUsername(email);
        if (user == null)
            throw new RestorePasswordException("User with the following email \'" + email + "\' doesn't not exist");

        String token = UUID.randomUUID().toString();
        restorePasswordDao.deleteByUser(user);
        restorePasswordDao.save(new RestorePassword(user, token, LocalDateTime.now()));
        emailService.sendRestorePasswordEmail(userMapper.toDto(user), token);

        LOGGER.debug("User " + email + " has requested password reset");
    }

    public void setNewPasswordForUser(String email, String password, String token) {
        User userEntity = userDao.findUserByUsername(email);

        if (userEntity == null)
            throw new RestorePasswordException("User with the following email \'" + email + "\' doesn't not exist");

        if (password == null || password.isEmpty())
            throw new RestorePasswordException("User \'" + email + "\' wrote empty password");

        if (token == null || token.isEmpty())
            throw new RestorePasswordException("User \'" + email + "\' wrote empty token");

        checkNotNull(restorePasswordDao.findByUser(userEntity));
        if (!restorePasswordDao.findByUser(userEntity).getToken().equals(token))
            throw new RestorePasswordException("User \'" + email + "\' wrote incorrect token");

        userEntity.setPassword(passwordEncoder.encode(password));
        userDao.save(userEntity);
        LOGGER.debug("User " + email + " has updated his password");
    }
}
