package ua.softserve.rv_028.issuecitymonitor.service;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserve.rv_028.issuecitymonitor.dao.PetitionDao;
import ua.softserve.rv_028.issuecitymonitor.dao.UserDao;
import ua.softserve.rv_028.issuecitymonitor.dto.PetitionDto;
import ua.softserve.rv_028.issuecitymonitor.entity.Petition;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static ua.softserve.rv_028.issuecitymonitor.Constants.DATE_FORMAT;

@Service
public class PetitionServiceImpl implements PetitionService {

    private static final Logger LOGGER = LogManager.getLogger(PetitionServiceImpl.class.getName());

    private final PetitionDao petitionDao;

    private final UserDao userDao;

    @Autowired
    public PetitionServiceImpl(PetitionDao petitionDao, UserDao userDao){
        this.petitionDao = petitionDao;
        this.userDao = userDao;
    }

    @Override
    public void deleteById(long id) {
        Petition petition = findOne(id);
        LOGGER.debug("Deleting " + petition.toString());
        petitionDao.delete(petition);
        LOGGER.debug("Deleted " + petition.toString());
    }

    @Override
    public List<PetitionDto> findAll() {
        LOGGER.debug("Finding all petitions");
        List<PetitionDto> petitionDtos = new ArrayList<>();
        for(Petition e : petitionDao.findAllByOrderByIdAsc()){
            petitionDtos.add(new PetitionDto(e));
        }
        LOGGER.debug("Found all petitions");
        return petitionDtos;
    }

    @Override
    public PetitionDto findById(long id) {
        LOGGER.debug("Finding petition by id " + id);
        Petition petition = findOne(id);
        LOGGER.debug("Found " + petition.toString());
        return new PetitionDto(petition);
    }

    @Override
    public PetitionDto update(PetitionDto petitionDto) {
        try {
            DATE_FORMAT.parse(petitionDto.getInitialDate());
        } catch (ParseException e) {
            throw new IllegalStateException("incorrect date");
        }

        Petition petition = findOne(petitionDto.getId());
        petition.setTitle(petitionDto.getTitle());
        petition.setDescription(petitionDto.getDescription());
        petition.setInitialDate(petitionDto.getInitialDate());
        petition.setCategory(petitionDto.getCategory());
        LOGGER.debug("Updating " + petition.toString());
        petitionDao.save(petition);
        LOGGER.debug("Updated " + petition.toString());
        return new PetitionDto(petition);
    }

    private Petition findOne(long id){
        Petition petition = petitionDao.findOne(id);
        if(petition == null){
            throw new IllegalStateException("petition id not found:" + id);
        }
        return petition;
    }
}