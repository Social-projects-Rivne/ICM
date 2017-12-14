package ua.softserve.rv_028.issuecitymonitor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ua.softserve.rv_028.issuecitymonitor.dao.UserDao;
import ua.softserve.rv_028.issuecitymonitor.dto.UserDto;
import ua.softserve.rv_028.issuecitymonitor.entity.User;

@Service
public class RegistrationServiceImpl implements RegistrationService{

    private final UserDao userDao;

    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationServiceImpl(UserDao userDao, BCryptPasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public boolean isUserExist(String email) {
        User user = userDao.findUserByUsername(email);
        if (user != null)
            return user.getUsername().equals(email);
        else return false;
    }

    @Override
    public void registrationUser(UserDto dto) {
        userDao.save(new User(dto.getFirstName(), dto.getLastName(), dto.getEmail(),
                passwordEncoder.encode(dto.getPassword())));
    }
}
