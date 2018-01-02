package ua.softserve.rv_028.issuecitymonitor.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ua.softserve.rv_028.issuecitymonitor.dto.PetitionDto;

import java.util.List;

public interface PetitionService {
    void deleteById(long id);
    Page<PetitionDto> findAllByPage(Pageable pageable);
    PetitionDto findById(long id);
    PetitionDto update(PetitionDto petitionDto);

    void addPetition(PetitionDto petitionDto);
}