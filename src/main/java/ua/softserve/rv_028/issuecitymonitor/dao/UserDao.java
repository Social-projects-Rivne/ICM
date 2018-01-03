package ua.softserve.rv_028.issuecitymonitor.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import ua.softserve.rv_028.issuecitymonitor.entity.User;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.UserRole;


public interface UserDao extends JpaRepository<User, Long> {
    List<User> findAllByOrderByIdAsc();
    List<User> findAll();

    User findUserByUsername(String username);

    Long countByUserRole(UserRole userRole);
    default Long countAdmins(){
        return countByUserRole(UserRole.ADMIN);

    }
}

