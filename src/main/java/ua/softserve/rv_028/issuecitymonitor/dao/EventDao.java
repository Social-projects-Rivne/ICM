package ua.softserve.rv_028.issuecitymonitor.dao;

import org.springframework.data.repository.CrudRepository;
import ua.softserve.rv_028.issuecitymonitor.entity.Event;

import java.util.List;

public interface EventDao extends CrudRepository<Event, Long> {
    List<Event> findAllByOrderByIdAsc();
}
