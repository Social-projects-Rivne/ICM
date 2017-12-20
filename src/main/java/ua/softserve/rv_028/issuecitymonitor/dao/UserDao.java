package ua.softserve.rv_028.issuecitymonitor.dao;



import java.util.List;

import org.springframework.data.repository.CrudRepository;


import ua.softserve.rv_028.issuecitymonitor.entity.User;

import java.util.List;

public interface UserDao extends CrudRepository<User, Long> {
    List<User> findAllByOrderByIdAsc();
    List<User> findAll();

    User findUserByUsername(String username);
}
