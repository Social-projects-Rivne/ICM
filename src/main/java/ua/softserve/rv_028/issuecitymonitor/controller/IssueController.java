package ua.softserve.rv_028.issuecitymonitor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.softserve.rv_028.issuecitymonitor.dto.IssueDto;
import ua.softserve.rv_028.issuecitymonitor.exception.IssueNotFoundException;
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
        LOG.info("show list issues");
        return service.findAll();
    }

    @PostMapping
    public IssueDto addIssue(IssueDto dto){
        LOG.info("add new issue");
        return service.addIssue(dto);
    }

    @PutMapping("/{id}")
    public IssueDto editIssue(IssueDto dto, Long id){
        LOG.info("edit issue");
        return service.editIssue(dto, id);
    }

    @DeleteMapping("/{id}")
    public void deleteIssue(@PathVariable Long id) throws IssueNotFoundException{
        try {
            LOG.info("delete issue");
            service.deleteIssue(id);
        }catch (IssueNotFoundException e){
            LOG.info("issue not found");
            e.printStackTrace();
        }
    }

}
