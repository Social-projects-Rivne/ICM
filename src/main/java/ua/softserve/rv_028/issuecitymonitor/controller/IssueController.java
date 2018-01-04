package ua.softserve.rv_028.issuecitymonitor.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import ua.softserve.rv_028.issuecitymonitor.dto.IssueDto;
import ua.softserve.rv_028.issuecitymonitor.dto.IssueLocationDto;
import ua.softserve.rv_028.issuecitymonitor.service.IssueService;

import static ua.softserve.rv_028.issuecitymonitor.Constants.PAGE_SIZE;

import java.util.List;

@RestController
@RequestMapping("/api/issues")
public class IssueController {

    private static final Logger LOGGER = Logger.getLogger(IssueController.class);

    private IssueService issueService;

    @Autowired
    public IssueController(IssueService service) {
        this.issueService = service;
    }

    @GetMapping("/{id}")
    public IssueDto getOne(@PathVariable long id){
        LOGGER.debug("GET request");
        return issueService.findById(id);
    }

    @GetMapping("/map")
    public List<IssueLocationDto> getAll(){
        LOGGER.debug("GET request");
        return issueService.findAll();
    }

    @GetMapping
    public Page<IssueDto> getAllByPage(@RequestParam(value = "page", defaultValue = "1") int page,
                                       @RequestParam(value = "size", defaultValue = (""+PAGE_SIZE)) int size){
        LOGGER.debug("GET request for all issues by page");
        return issueService.findAllByPage(page, size);
    }

    @PostMapping
    public IssueDto addIssue(@RequestBody IssueDto dto){
        LOGGER.debug("POST request");
        return issueService.addIssue(dto);
    }

    @PutMapping("/{id}")
    public IssueDto editIssue(@RequestBody IssueDto dto){
        LOGGER.debug("PUT request");
        return issueService.update(dto);
    }

    @DeleteMapping("/{id}")
    public void deleteIssue(@PathVariable long id) {
        LOGGER.debug("DELETE request");
        issueService.deleteById(id);
    }
}
