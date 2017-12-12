package ua.softserve.rv_028.issuecitymonitor.service;

import ua.softserve.rv_028.issuecitymonitor.dto.PetitionDto;

import java.util.List;

public interface PetitionService {
    //    PetitionDto add(PetitionDto petitionDto);
    void deleteById(long id);
    List<PetitionDto> findAll();
    PetitionDto findById(long id);
    PetitionDto update(PetitionDto petitionDto);
}