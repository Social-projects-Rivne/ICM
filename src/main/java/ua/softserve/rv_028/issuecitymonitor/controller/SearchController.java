package ua.softserve.rv_028.issuecitymonitor.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.softserve.rv_028.issuecitymonitor.dto.EventDto;
import ua.softserve.rv_028.issuecitymonitor.dto.IssueDto;
import ua.softserve.rv_028.issuecitymonitor.dto.PetitionDto;
import ua.softserve.rv_028.issuecitymonitor.dto.UserDto;
import ua.softserve.rv_028.issuecitymonitor.service.SearchService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    private static final Logger LOGGER = Logger.getLogger(SearchController.class);

    private final SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/events")
    public Page<EventDto> searchEvents(@RequestParam(name = "page", defaultValue = "1") int pageNumber,
                                       @RequestParam(name = "size", defaultValue = "20") int pageSize,
                                       @RequestParam Map<String, String> queryMap) {
        LOGGER.debug("Searching events by criteria" + queryMap.toString());
        return searchService.findEventsByCriteria(queryMap, pageNumber, pageSize);
    }

    @GetMapping("/users")
    public Page<UserDto> searchUsers(@RequestParam(name = "page", defaultValue = "1") int pageNumber,
                                     @RequestParam(name = "size", defaultValue = "20") int pageSize,
                                     @RequestParam Map<String, String> queryMap) {
        LOGGER.debug("Searching users by criteria" + queryMap.toString());
        return searchService.findUsersByCriteria(queryMap, pageNumber, pageSize);
    }

    @GetMapping("/issues")
    public Page<IssueDto> searchIssues(@RequestParam(name = "page", defaultValue = "1") int pageNumber,
                                       @RequestParam(name = "size", defaultValue = "20") int pageSize,
                                       @RequestParam Map<String, String> queryMap) {
        LOGGER.debug("Searching issues by criteria" + queryMap.toString());
        return searchService.findIssuesByCriteria(queryMap, pageNumber, pageSize);
    }

    @GetMapping("/petitions")
    public Page<PetitionDto> searchPetitions(@RequestParam(name = "page", defaultValue = "1") int pageNumber,
                                             @RequestParam(name = "size", defaultValue = "20") int pageSize,
                                             @RequestParam Map<String, String> queryMap) {
        LOGGER.debug("Searching petitions by criteria" + queryMap.toString());
        return searchService.findPetitionsByCriteria(queryMap, pageNumber, pageSize);
    }
}
