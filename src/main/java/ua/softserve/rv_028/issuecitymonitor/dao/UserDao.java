package ua.softserve.rv_028.issuecitymonitor.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ua.softserve.rv_028.issuecitymonitor.entity.User;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.UserRole;

public interface UserDao extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    User findUserByUsername(String username);

    Long countByUserRole(UserRole userRole);
    default Long countAdmins(){
        return countByUserRole(UserRole.ADMIN);
    }
}

