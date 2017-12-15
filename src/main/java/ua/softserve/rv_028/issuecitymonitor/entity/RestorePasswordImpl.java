package ua.softserve.rv_028.issuecitymonitor.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserve.rv_028.issuecitymonitor.dao.RestorePasswordDao;
import ua.softserve.rv_028.issuecitymonitor.dao.UserDao;

import java.util.Date;
import java.util.UUID;

import static ua.softserve.rv_028.issuecitymonitor.Constants.DATE_FORMAT;

@Service
public class RestorePasswordImpl implements RestorePasswordService {

    @Autowired
    UserDao userDao;

    @Autowired
    RestorePasswordDao restorePasswordDao;

    @Override
    public boolean createOrderRestore(String email) {
        User user = userDao.findUserByUsername(email);
        if (user.getUsername().equals(email)){
            restorePasswordDao.save(new RestorePassword(
                    user,
                    UUID.randomUUID().toString(),
                    DATE_FORMAT.format(new Date())));


            return true;
        } else {
            return false;
        }
    }
}
