package ua.softserve.rv_028.issuecitymonitor.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ua.softserve.rv_028.issuecitymonitor.entity.Event;

public interface EventDao extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {
}
