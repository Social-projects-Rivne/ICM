package ua.softserve.rv_028.issuecitymonitor.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.softserve.rv_028.issuecitymonitor.dao.UserDao;

/**
 * This class is implemented by the interface {@link UserDetailsService} from Spring Security.
 * The method from UserDetailService interface return UserDetail.
 *
 * @version     1.0 07 Dec 2017
 * @author      gefasim
 */
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserDetailsServiceImpl implements UserDetailsService {

    UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        return userDao.findUserByUsername(email);
    }
}