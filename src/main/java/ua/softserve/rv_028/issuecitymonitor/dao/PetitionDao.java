package ua.softserve.rv_028.issuecitymonitor.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import ua.softserve.rv_028.issuecitymonitor.entity.Petition;

import java.util.List;

public interface PetitionDao extends PagingAndSortingRepository<Petition, Long> {
    List<Petition> findAllByOrderByIdAsc();
}