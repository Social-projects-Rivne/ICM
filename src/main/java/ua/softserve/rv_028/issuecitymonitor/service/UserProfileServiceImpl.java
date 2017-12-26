package ua.softserve.rv_028.issuecitymonitor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserve.rv_028.issuecitymonitor.dao.UserDao;
import ua.softserve.rv_028.issuecitymonitor.entity.User;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    private UserDao userDao;

    @Autowired
    public UserProfileServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void setNewPassword(String email, String oldPassword, String newPassword) {
        User user = userDao.findUserByUsername(email);
        if (user == null)
            throw new IllegalStateException();

    }
}
