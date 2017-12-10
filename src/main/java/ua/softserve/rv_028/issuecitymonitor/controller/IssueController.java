package ua.softserve.rv_028.issuecitymonitor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.softserve.rv_028.issuecitymonitor.dto.IssueDto;
import ua.softserve.rv_028.issuecitymonitor.service.IssueService;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("api/issues")
public class IssueController {

    private static final Logger LOG = Logger.getLogger(IssueController.class.getName());

    private IssueService service;

    @Autowired
    public IssueController(IssueService service) {
        this.service = service;
    }

    @GetMapping
    public List<IssueDto> getAll(){

        return service.findAll();
    }

    @GetMapping("/{id}")
    public IssueDto getOne(@PathVariable long id){

        return service.findById(id);
    }

    @PostMapping
    public IssueDto addIssue(@RequestBody IssueDto dto){

        return service.addIssue(dto);
    }

    @PutMapping("/{id}")
    public IssueDto editIssue(@RequestBody IssueDto dto){

        return service.editIssue(dto);
    }

    @DeleteMapping("/{id}")
    public void deleteIssue(@PathVariable long id) {

            service.deleteIssue(id);
    }
}
