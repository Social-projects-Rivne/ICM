package ua.softserve.rv_028.issuecitymonitor.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ua.softserve.rv_028.issuecitymonitor.dao.PetitionDao;
import ua.softserve.rv_028.issuecitymonitor.dao.UserDao;
import ua.softserve.rv_028.issuecitymonitor.dto.PetitionDto;
import ua.softserve.rv_028.issuecitymonitor.entity.Petition;
import ua.softserve.rv_028.issuecitymonitor.entity.User;
import ua.softserve.rv_028.issuecitymonitor.service.mappers.PetitionMapper;

import java.time.LocalDateTime;

import static ua.softserve.rv_028.issuecitymonitor.Constants.DATE_FORMAT;

@Service
@Log4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PetitionService {

    PetitionDao petitionDao;

    UserDao userDao;

    PetitionMapper petitionMapper;

    public void addPetition(PetitionDto dto) {
        try {
            Authentication userAuth = SecurityContextHolder.getContext().getAuthentication();
            User user = userDao.findUserByUsername(userAuth.getName());
            petitionDao.save(new Petition(user, dto.getTitle(), dto.getDescription(), LocalDateTime.parse(dto.getInitialDate(), DATE_FORMAT), dto.getCategory()));
            log.debug("Added petition");
        } catch (RuntimeException e){
            throw new IllegalArgumentException("petition add failed", e);
        }
    }

    public Page<PetitionDto> findAllByPage(int pageNumber, int pageSize, Sort.Direction direction, String columns) {
        String[] columnArray = columns.split(",");
        PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize, direction, columnArray);
        Page<Petition> petitions = petitionDao.findAll(pageRequest);
        return petitionMapper.toDtoPage(petitions);
    }

    public PetitionDto findById(long id) {
        Petition petition = findOne(id);
        log.debug("Found " + petition.toString());
        return petitionMapper.toDto(petition);
    }

    public PetitionDto update(PetitionDto petitionDto) {
        Petition petition = findOne(petitionDto.getId());

        petition.setTitle(petitionDto.getTitle());
        petition.setDescription(petitionDto.getDescription());
        petition.setInitialDate(LocalDateTime.parse(petitionDto.getInitialDate(), DATE_FORMAT));
        petition.setCategory(petitionDto.getCategory());

        petition = petitionDao.save(petition);
        log.debug("Updated " + petition.toString());
        return petitionMapper.toDto(petition);
    }

    public void deleteById(long id) {
        Petition petition = findOne(id);
        petitionDao.delete(petition);
        log.debug("Deleted " + petition.toString());
    }

    private Petition findOne(long id){
        Petition petition = petitionDao.findOne(id);
        if(petition == null){
            throw new IllegalArgumentException("petition id not found:" + id);
        }
        return petition;
    }
}