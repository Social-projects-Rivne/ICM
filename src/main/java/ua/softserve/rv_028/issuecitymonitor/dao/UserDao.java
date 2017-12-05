package ua.softserve.rv_028.issuecitymonitor.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.softserve.rv_028.issuecitymonitor.entity.User;

public interface UserDao extends JpaRepository<User, Long> {
}
