package ua.softserve.rv_028.issuecitymonitor.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ua.softserve.rv_028.issuecitymonitor.Constants;
import ua.softserve.rv_028.issuecitymonitor.dao.UserDao;
import ua.softserve.rv_028.issuecitymonitor.entity.User;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    private UserDao userDao;
    private BCryptPasswordEncoder encoder;

    private static final Logger LOGGER = Logger.getLogger(UserProfileServiceImpl.class.getName());

    @Autowired
    public UserProfileServiceImpl(UserDao userDao, BCryptPasswordEncoder encoder) {
        this.userDao = userDao;
        this.encoder = encoder;
    }

    @Override
    public void updatePassword(String email, String oldPassword, String newPassword) {
        User user = userDao.findUserByUsername(email);
        checkState(user != null, "The user " + email + " not found");
        checkArgument(encoder.matches(oldPassword, user.getPassword()), "The user " + email
                + " entered an incorrect password");
        checkArgument(newPassword.length() >= 2, Constants.SHORT_PASSWORD);
        user.setPassword(encoder.encode(newPassword));
        userDao.save(user);
        LOGGER.debug("User " + user.getUsername() + " has changed his password");
    }

    @Override
    public void updateContactsInfo(String email, String fistName, String lastName, String phone) {
        User user = userDao.findUserByUsername(email);
        user.setFirstName(fistName);
        user.setLastName(lastName);
        user.setPhone(phone);
        userDao.save(user);
        System.out.println(user);
    }
}
