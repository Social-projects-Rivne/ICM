package ua.softserve.rv_028.issuecitymonitor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.softserve.rv_028.issuecitymonitor.dao.UserDao;
import ua.softserve.rv_028.issuecitymonitor.entity.User;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.UserRole;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.UserStatus;

/**
 * This class is implemented by the interface {@link UserDetailsService} from Spring Security.
 * The method from UserDetailService interface return UserDetail.
 *
 * @version     1.0 07 Dec 2017
 * @author      gefasim
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserDao userDao;

    @Autowired
    public UserDetailsServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * This method return UserDetails model for AuthenticationManagerBuilder in Security class
     *
     * @param email of table {@link User}
     * @return UserDetails
     * @throws UsernameNotFoundException for if any model field is incorrect
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userDao.findUserByUsername(email);
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                true, true, true,
                (user.getUserStatus()== UserStatus.ACTIVE || user.getUserStatus() == UserStatus.UNCONFIRMED),
                UserRole.collectionForRole(user.getUserRole()));
    }
}