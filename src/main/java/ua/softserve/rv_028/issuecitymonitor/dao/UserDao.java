package ua.softserve.rv_028.issuecitymonitor.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ua.softserve.rv_028.issuecitymonitor.entity.User;

import java.util.List;

public interface UserDao extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    List<User> findAll();
    User findUserByUsername(String username);
}
