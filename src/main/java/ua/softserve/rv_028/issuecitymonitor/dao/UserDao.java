package ua.softserve.rv_028.issuecitymonitor.dao;


<<<<<<< HEAD
=======

import java.util.Set;
>>>>>>> 69db67648c4384c5fd3c640bebd4636ff79e8d0d

import org.springframework.data.repository.CrudRepository;

import ua.softserve.rv_028.issuecitymonitor.entity.User;

<<<<<<< HEAD
import java.util.List;

public interface UserDao extends CrudRepository<User, Long> {
    List<User> findAllByOrderByIdAsc();
=======
    Set<User> findAll();
    User findUserByUsername(String username);

>>>>>>> 69db67648c4384c5fd3c640bebd4636ff79e8d0d
}
