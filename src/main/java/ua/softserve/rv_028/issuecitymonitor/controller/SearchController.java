package ua.softserve.rv_028.issuecitymonitor.controller;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j;
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

import java.util.Map;

import static ua.softserve.rv_028.issuecitymonitor.Constants.PAGE_SIZE;

@RestController
@RequestMapping("/api/search")
@Log4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class SearchController {

    SearchService searchService;

    @GetMapping("/events")
    public Page<EventDto> searchEvents(@RequestParam(name = "page", defaultValue = "1") int pageNumber,
                                       @RequestParam(name = "size", defaultValue = (""+PAGE_SIZE)) int pageSize,
                                       @RequestParam Map<String, String> queryMap) {
        log.debug("Searching events by criteria" + queryMap.toString());
        return searchService.findEventsByCriteria(queryMap, pageNumber, pageSize);
    }

    @GetMapping("/users")
    public Page<UserDto> searchUsers(@RequestParam(name = "page", defaultValue = "1") int pageNumber,
                                     @RequestParam(name = "size", defaultValue = (""+PAGE_SIZE)) int pageSize,
                                     @RequestParam Map<String, String> queryMap) {
        log.debug("Searching users by criteria" + queryMap.toString());
        return searchService.findUsersByCriteria(queryMap, pageNumber, pageSize);
    }

    @GetMapping("/issues")
    public Page<IssueDto> searchIssues(@RequestParam(name = "page", defaultValue = "1") int pageNumber,
                                       @RequestParam(name = "size", defaultValue = (""+PAGE_SIZE)) int pageSize,
                                       @RequestParam Map<String, String> queryMap) {
        log.debug("Searching issues by criteria" + queryMap.toString());
        return searchService.findIssuesByCriteria(queryMap, pageNumber, pageSize);
    }

    @GetMapping("/petitions")
    public Page<PetitionDto> searchPetitions(@RequestParam(name = "page", defaultValue = "1") int pageNumber,
                                             @RequestParam(name = "size", defaultValue = (""+PAGE_SIZE)) int pageSize,
                                             @RequestParam Map<String, String> queryMap) {
        log.debug("Searching petitions by criteria" + queryMap.toString());
        return searchService.findPetitionsByCriteria(queryMap, pageNumber, pageSize);
    }
}
