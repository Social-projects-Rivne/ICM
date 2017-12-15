package ua.softserve.rv_028.issuecitymonitor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserve.rv_028.issuecitymonitor.dao.RestorePasswordDao;
import ua.softserve.rv_028.issuecitymonitor.dao.UserDao;
import ua.softserve.rv_028.issuecitymonitor.entity.RestorePassword;
import ua.softserve.rv_028.issuecitymonitor.entity.User;

import java.util.Date;
import java.util.UUID;

import static ua.softserve.rv_028.issuecitymonitor.Constants.DATE_FORMAT;

@Service
public class RestorePasswordImpl implements RestorePasswordService {

    @Autowired
    UserDao userDao;

    @Autowired
    RestorePasswordDao restorePasswordDao;

    @Autowired
    EmailService emailService;

    @Autowired
    MapperService mapperService;

    @Override
    public boolean createOrderRestore(String email) {
        User user = userDao.findUserByUsername(email);
        if (user.getUsername().equals(email)){
            String token = UUID.randomUUID().toString();


            restorePasswordDao.save(new RestorePassword(
                    user,
                    UUID.randomUUID().toString(),
                    DATE_FORMAT.format(new Date())));

            emailService.sendRestorePasswordEmail(mapperService.fromEntityToDto(user), token);

            return true;
        } else {
            return false;
        }
    }

}
