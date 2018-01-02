package ua.softserve.rv_028.issuecitymonitor.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.softserve.rv_028.issuecitymonitor.Constants;
import ua.softserve.rv_028.issuecitymonitor.dao.UserDao;
import ua.softserve.rv_028.issuecitymonitor.entity.User;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    private UserDao userDao;
    private BCryptPasswordEncoder encoder;

    private static final Logger LOGGER = Logger.getLogger(UserProfileServiceImpl.class.getName());
    private final Path rootLocation = Paths.get("photos");

    @Autowired
    public UserProfileServiceImpl(UserDao userDao, BCryptPasswordEncoder encoder) {
        this.userDao = userDao;
        this.encoder = encoder;
    }

    @Override
    public void updatePassword(String email, String oldPassword, String newPassword) {
        User user = userDao.findUserByUsername(email);
        checkArgument(user != null, "The user " + email + " not found");
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
        checkArgument(user != null, "The user " + email + " not found");
        if (fistName != null && !fistName.isEmpty())
            user.setFirstName(fistName);
        if (lastName != null && !lastName.isEmpty())
            user.setLastName(lastName);
        if (phone != null && !phone.isEmpty())
            user.setPhone(phone);
        userDao.save(user);
        LOGGER.debug("User " + user.getUsername() + " has changed his contacts form");
    }

    @Override
    public void updatePortfolioPhoto(MultipartFile photo){
        try {
            // TODO: https://stackoverflow.com/questions/21063140/saving-file-to-resource-directory-using-spring
            Path path = Paths.get("src/main/resources/photos", photo.getOriginalFilename());
            Files.write(path, photo.getBytes());
        } catch (Exception e) {
            throw new RuntimeException("FAIL!");
        }
    }

    @Override
    public Map getUserInfo(String email) {
        User user = userDao.findUserByUsername(email);
        checkArgument(user != null, "The user " + email + " not found");
        HashMap<String, Object> map = new HashMap<>();
        map.put("email", user.getUsername());
        map.put("firstName", user.getFirstName());
        map.put("lastName", user.getLastName());
        map.put("authorities", user.getAuthorities());
        map.put("phone", user.getPhone());
        return map;
    }
}