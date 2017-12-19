package ua.softserve.rv_028.issuecitymonitor.service;

import ua.softserve.rv_028.issuecitymonitor.dto.EventDto;
import ua.softserve.rv_028.issuecitymonitor.dto.IssueDto;
import ua.softserve.rv_028.issuecitymonitor.dto.UserDto;

import java.util.List;
import java.util.Map;

public interface SearchService {
    List<EventDto> findEventsByCriteria(Map<String, String> queryMap);
    List<UserDto> findUsersByCriteria(Map<String, String> queryMap);
    List<IssueDto> findIssuesByCriteria(Map<String, String> queryMap);
//    List<PetitionDto> findPetitionsByCriteria(Map<String, String> queryMap);
}
