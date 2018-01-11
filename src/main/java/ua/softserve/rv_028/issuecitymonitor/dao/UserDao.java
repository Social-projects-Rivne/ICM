package ua.softserve.rv_028.issuecitymonitor.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import ua.softserve.rv_028.issuecitymonitor.entity.User;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.UserRole;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.UserStatus;

public interface UserDao extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    @Query(value = "SELECT u FROM User u WHERE NOT (u.userStatus = '0') AND (?1 = false)")
    Page<User> findAll(boolean isDeleted, Pageable pageable);

    User findUserByUsername(String username);
    User findById(Long id);

    @Query(value = "SELECT COUNT(u) FROM User u WHERE NOT (u.userStatus = '0')")
    long count();

    long countByUserRoleAndUserStatusIsNot(UserRole userRole, UserStatus userStatus);
    default long countAdmins(){
        return countByUserRoleAndUserStatusIsNot(UserRole.ADMIN, UserStatus.DELETED);
    }
}

