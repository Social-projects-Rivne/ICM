package ua.softserve.rv_028.issuecitymonitor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.softserve.rv_028.issuecitymonitor.dto.IssueDto;
import ua.softserve.rv_028.issuecitymonitor.service.IssueService;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("api/issues")
public class IssueController {

    private static final Logger LOG = Logger.getLogger(IssueController.class.getName());

    @Autowired
    private IssueService service;

    @GetMapping
    public ResponseEntity<?> getAll(){
        LOG.info("show list issues");
        return new ResponseEntity<Object>(service.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addIssue(IssueDto dto){
        LOG.info("add new issue");
        return new ResponseEntity<Object>(service.addIssue(dto), HttpStatus.OK);
    }

    @PutMapping("{/id}")
    public ResponseEntity<?> editIssue(IssueDto dto, Long id){
        LOG.info("edit issue");
        return new ResponseEntity<Object>(service.editIssue(dto, id), HttpStatus.OK);
    }

    @DeleteMapping("{/id}")
    public ResponseEntity<?> deleteIssue(Long id){
        LOG.info("delete issue");
        service.deleteIssue(id);
        return new ResponseEntity<Object>(HttpStatus.OK);
    }

}
