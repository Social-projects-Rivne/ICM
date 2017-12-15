package ua.softserve.rv_028.issuecitymonitor.dao;

import org.springframework.data.repository.CrudRepository;
import ua.softserve.rv_028.issuecitymonitor.entity.RestorePassword;

import java.util.List;

public interface RestorePasswordDao extends CrudRepository<RestorePassword, Long>{
    List<RestorePassword> findAll();
}
