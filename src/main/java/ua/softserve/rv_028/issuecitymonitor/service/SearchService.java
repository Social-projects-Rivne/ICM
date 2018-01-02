package ua.softserve.rv_028.issuecitymonitor.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ua.softserve.rv_028.issuecitymonitor.dao.EventDao;
import ua.softserve.rv_028.issuecitymonitor.dao.IssueDao;
import ua.softserve.rv_028.issuecitymonitor.dao.PetitionDao;
import ua.softserve.rv_028.issuecitymonitor.dao.UserDao;
import ua.softserve.rv_028.issuecitymonitor.dto.EventDto;
import ua.softserve.rv_028.issuecitymonitor.dto.IssueDto;
import ua.softserve.rv_028.issuecitymonitor.dto.PetitionDto;
import ua.softserve.rv_028.issuecitymonitor.dto.UserDto;
import ua.softserve.rv_028.issuecitymonitor.service.mappers.EventMapper;
import ua.softserve.rv_028.issuecitymonitor.service.mappers.IssueMapper;
import ua.softserve.rv_028.issuecitymonitor.service.mappers.PetitionMapper;
import ua.softserve.rv_028.issuecitymonitor.service.mappers.UserMapper;
import ua.softserve.rv_028.issuecitymonitor.service.specifiation.EventSpecification;
import ua.softserve.rv_028.issuecitymonitor.service.specifiation.IssueSpecification;
import ua.softserve.rv_028.issuecitymonitor.service.specifiation.PetitionSpecification;
import ua.softserve.rv_028.issuecitymonitor.service.specifiation.UserSpecification;

import java.util.Map;

@Service
public class SearchService {

    private final static Logger LOGGER = Logger.getLogger(SearchService.class);

    private final EventDao eventDao;
    private final UserDao userDao;
    private final IssueDao issueDao;
    private final PetitionDao petitionDao;

    private final UserMapper userMapper;
    private final EventMapper eventMapper;
    private final IssueMapper issueMapper;
    private final PetitionMapper petitionMapper;

    @Autowired
    public SearchService(EventDao eventDao, UserMapper userMapper, UserDao userDao, IssueDao issueDao,
                         PetitionDao petitionDao, EventMapper eventMapper, IssueMapper issueMapper,
                         PetitionMapper petitionMapper) {
        this.eventDao = eventDao;
        this.userMapper = userMapper;
        this.userDao = userDao;
        this.issueDao = issueDao;
        this.petitionDao = petitionDao;
        this.eventMapper = eventMapper;
        this.issueMapper = issueMapper;
        this.petitionMapper = petitionMapper;
    }

    public Page<EventDto> findEventsByCriteria(Map<String, String> queryMap, int pageNumber, int pageSize) {
        PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize, Sort.Direction.ASC, "id");
        Page<EventDto> eventDtos = eventMapper.toDtoPage(eventDao.findAll(new EventSpecification(queryMap), pageRequest));
        LOGGER.debug("Events search successful");
        return eventDtos;
    }

    public Page<UserDto> findUsersByCriteria(Map<String, String> queryMap, int pageNumber, int pageSize) {
        PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize, Sort.Direction.ASC, "id");
        Page<UserDto> userDtos = userMapper.toDtoPage(userDao.findAll(new UserSpecification(queryMap), pageRequest));
        LOGGER.debug("Users search successful");
        return userDtos;
    }

    public Page<IssueDto> findIssuesByCriteria(Map<String, String> queryMap, int pageNumber, int pageSize) {
        PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize, Sort.Direction.ASC, "id");
        Page<IssueDto> issueDtos = issueMapper.toDtoPage(issueDao.findAll(new IssueSpecification(queryMap), pageRequest));
        LOGGER.debug("Issues search successful");
        return issueDtos;
    }

    public Page<PetitionDto> findPetitionsByCriteria(Map<String, String> queryMap, int pageNumber, int pageSize) {
        PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize, Sort.Direction.ASC, "id");
        Page<PetitionDto> petitionDtos = petitionMapper.toDtoPage(petitionDao.findAll(new PetitionSpecification(queryMap), pageRequest));
        LOGGER.debug("Petitions search successful");
        return petitionDtos;
    }
}
