package ua.softserve.rv_028.issuecitymonitor.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.softserve.rv_028.issuecitymonitor.dto.EventDto;
import ua.softserve.rv_028.issuecitymonitor.service.SearchService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/search")
public class SearchController {
    private static final Logger LOGGER = Logger.getLogger(SearchController.class.getName());

    private final SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/events")
    public List<EventDto> searchEvents(@RequestParam Map<String, String> queryMap) {
        LOGGER.debug("Searching events by criteria" + queryMap.toString());
        return searchService.findByCriteria(queryMap);
    }
}
