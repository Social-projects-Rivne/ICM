package ua.softserve.rv_028.issuecitymonitor.dao;

import org.springframework.data.repository.CrudRepository;
import ua.softserve.rv_028.issuecitymonitor.entity.User;

import java.util.Set;

public interface UserDao extends CrudRepository<User, Long>{

    Set<User> findAll();
    User findUserByUsername(String username);
<<<<<<< HEAD
}
=======
}
>>>>>>> c6ffabb528d995cb3911e068b27a396bbc178b78
