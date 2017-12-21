package ua.softserve.rv_028.issuecitymonitor.dao;



import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import ua.softserve.rv_028.issuecitymonitor.entity.User;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.UserRole;

import java.util.List;

public interface UserDao extends CrudRepository<User, Long> {
    List<User> findAllByOrderByIdAsc();
    Set<User> findAll();
    User findUserByUsername(String username);
    Long countByUserRole(UserRole userRole);
}
