package ua.softserve.rv_028.issuecitymonitor.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import ua.softserve.rv_028.issuecitymonitor.dto.IssueDto;
import ua.softserve.rv_028.issuecitymonitor.dto.IssueLocationDto;
import ua.softserve.rv_028.issuecitymonitor.service.IssueService;

import java.util.List;

@RestController
@RequestMapping("/api/issues")
public class IssueController {

    private static final Logger LOGGER = Logger.getLogger(IssueController.class.getName());

    private IssueService service;

    @Autowired
    public IssueController(IssueService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public IssueDto getOne(@PathVariable long id){
        LOGGER.debug("GET request");
        LOGGER.debug("GET request successful");
        return service.findById(id);
    }

    @GetMapping("/map")
    public List<IssueLocationDto> getAll(){
        LOGGER.debug("GET request");
        LOGGER.debug("GET request successful");
        return service.findAll();
    }

    @GetMapping
    public Page<IssueDto> getAllByPage(@RequestParam(value = "page", defaultValue = "1") int page,
                                       @RequestParam(value = "size", defaultValue = "10") int size){
        LOGGER.debug("GET request for all issues by page");
        return service.findAllByPage(new PageRequest(page-1, size));
    }

    @PostMapping
    public IssueDto addIssue(@RequestBody IssueDto dto){
        LOGGER.debug("POST request");
        LOGGER.debug("POST request successful");
        return service.addIssue(dto);
    }

    @PutMapping("/{id}")
    public IssueDto editIssue(@RequestBody IssueDto dto){
        LOGGER.debug("PUT request");
        LOGGER.debug("PUT request successful");
        return service.editIssue(dto);
    }

    @DeleteMapping("/{id}")
    public void deleteIssue(@PathVariable long id) {
        LOGGER.debug("DELETE request");
        LOGGER.debug("DELETE request successful");
            service.deleteIssue(id);
    }
}
