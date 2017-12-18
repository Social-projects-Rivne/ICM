package ua.softserve.rv_028.issuecitymonitor.dao;

import org.springframework.data.repository.CrudRepository;
import ua.softserve.rv_028.issuecitymonitor.entity.RestorePassword;
import ua.softserve.rv_028.issuecitymonitor.entity.User;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface RestorePasswordDao extends CrudRepository<RestorePassword, Long>{
    List<RestorePassword> findAll();
    void deleteByUser(User user);
    RestorePassword findByUser(User user);
}
