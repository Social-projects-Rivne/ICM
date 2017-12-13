package ua.softserve.rv_028.issuecitymonitor.dao;

import org.springframework.data.repository.CrudRepository;
import ua.softserve.rv_028.issuecitymonitor.entity.User;


import java.util.Set;

public interface UserDao extends CrudRepository<User, Long>{

    Set<User> findAll();
    User findUserByUsername(String username);

}
