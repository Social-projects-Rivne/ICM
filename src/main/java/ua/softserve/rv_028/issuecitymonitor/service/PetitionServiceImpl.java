package ua.softserve.rv_028.issuecitymonitor.service;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ua.softserve.rv_028.issuecitymonitor.dao.PetitionDao;
import ua.softserve.rv_028.issuecitymonitor.dao.UserDao;
import ua.softserve.rv_028.issuecitymonitor.dto.PetitionDto;
import ua.softserve.rv_028.issuecitymonitor.entity.Petition;

import ua.softserve.rv_028.issuecitymonitor.entity.User;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static ua.softserve.rv_028.issuecitymonitor.Constants.DATE_FORMAT;

@Service
public class PetitionServiceImpl implements PetitionService {

    private static final Logger LOGGER = LogManager.getLogger(PetitionServiceImpl.class.getName());

    private final PetitionDao petitionDao;

    private final UserDao userDao;

    private MapperService mapperService;

    @Autowired
    public PetitionServiceImpl(PetitionDao petitionDao, UserDao userDao, MapperService mapperService){
        this.petitionDao = petitionDao;
        this.userDao = userDao;
        this.mapperService = mapperService;
    }

    @Override
    public void deleteById(long id) {
        Petition petition = findOne(id);
        LOGGER.debug("Deleting " + petition.toString());
        petitionDao.delete(petition);
        LOGGER.debug("Deleted " + petition.toString());
    }

    @Override
    public Page<PetitionDto> findAllByPage(Pageable pageable) {
        Page<Petition> petitions = petitionDao.findAll(pageable);
        return petitions.map(mapperService::fromEntityToDto);
    }

    @Override
    public PetitionDto findById(long id) {
        LOGGER.debug("Finding petition by id " + id);
        Petition petition = findOne(id);
        LOGGER.debug("Found " + petition.toString());
        return mapperService.fromEntityToDto(petition);
    }

    @Override
    public PetitionDto update(PetitionDto petitionDto) {

        Petition petition = findOne(petitionDto.getId());
        petition.setTitle(petitionDto.getTitle());
        petition.setDescription(petitionDto.getDescription());
        petition.setInitialDate(petitionDto.getInitialDate());
        petition.setCategory(petitionDto.getCategory());
        LOGGER.debug("Updating " + petition.toString());
        petitionDao.save(petition);
        LOGGER.debug("Updated " + petition.toString());
        return mapperService.fromEntityToDto(petition);
    }

    private Petition findOne(long id){
        Petition petition = petitionDao.findOne(id);
        if(petition == null){
            throw new IllegalStateException("petition id not found:" + id);
        }
        return petition;
    }

    @Override
    public void addPetition(PetitionDto dto) {
        try {

            Authentication userAuth = SecurityContextHolder.getContext().getAuthentication();
            User user1 = userDao.findUserByUsername(userAuth.getName());
            petitionDao.save(new Petition(user1, dto.getTitle(), dto.getDescription(), dto.getInitialDate(), dto.getCategory()));
        }

        catch (RuntimeException e){
            throw new IllegalArgumentException("Petition add Failed", e);
        }
    }

}