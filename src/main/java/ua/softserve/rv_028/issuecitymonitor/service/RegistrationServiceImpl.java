package ua.softserve.rv_028.issuecitymonitor.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ua.softserve.rv_028.issuecitymonitor.controller.AdviceController;
import ua.softserve.rv_028.issuecitymonitor.dao.UserDao;
import ua.softserve.rv_028.issuecitymonitor.dto.UserDto;
import ua.softserve.rv_028.issuecitymonitor.entity.User;
import ua.softserve.rv_028.issuecitymonitor.exception.RegistrationException;
import ua.softserve.rv_028.issuecitymonitor.service.mappers.MapperService;
import ua.softserve.rv_028.issuecitymonitor.service.mappers.UserMapper;

@Service
public class RegistrationServiceImpl implements RegistrationService{

    private UserDao userDao;
    private EmailService emailService;
    private UserMapper mapperService;
    private BCryptPasswordEncoder passwordEncoder;
    private static final Logger LOGGER = Logger.getLogger(AdviceController.class.getName());

    @Autowired
    public RegistrationServiceImpl(UserMapper mapperService, UserDao userDao, EmailService emailService,
                                   BCryptPasswordEncoder passwordEncoder) {
        this.mapperService = mapperService;
        this.userDao = userDao;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean isPossibleRegistration(String email) {
        User user = userDao.findUserByUsername(email);
        return isUserExist(email) && !user.getIsDeleted();
    }

    @Override
    public UserDto registrationUser(UserDto dto) {
        try {
            User user = userDao.save(new User(dto.getFirstName(), dto.getLastName(), dto.getEmail(),
                    passwordEncoder.encode(dto.getPassword())));
            LOGGER.info("The user " + dto.getEmail() + " has been registered");
            emailService.sendGreetingEmail(dto.getEmail(), dto.getFirstName(), dto.getLastName());
            return mapperService.toDto(user);
        } catch (RuntimeException e){
            throw new RegistrationException();
        }
    }

    private boolean isUserExist(String email){
        User user = userDao.findUserByUsername(email);
        return user != null && user.getUsername().equals(email);
    }
}
