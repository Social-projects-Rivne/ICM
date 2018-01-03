package ua.softserve.rv_028.issuecitymonitor.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.softserve.rv_028.issuecitymonitor.entity.Petition;

import java.util.List;

public interface PetitionDao extends JpaRepository<Petition, Long> {
    List<Petition> findAllByOrderByIdAsc();
}