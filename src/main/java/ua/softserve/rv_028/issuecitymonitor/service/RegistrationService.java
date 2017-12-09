package ua.softserve.rv_028.issuecitymonitor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserve.rv_028.issuecitymonitor.dao.UserDao;
import ua.softserve.rv_028.issuecitymonitor.entity.User;

@Service
public class RegistrationService {

    @Autowired
    UserDao userDao;

    public boolean existUser(String email) {
        User user = userDao.findByEmail(email);
        return user != null && user.getEmail().equals(email);
    }
}
