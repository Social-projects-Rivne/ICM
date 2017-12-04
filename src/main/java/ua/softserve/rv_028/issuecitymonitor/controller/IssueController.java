package ua.softserve.rv_028.issuecitymonitor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.softserve.rv_028.issuecitymonitor.dto.IssueDto;
import ua.softserve.rv_028.issuecitymonitor.service.IssueService;

import java.util.List;

@RestController
@RequestMapping("api/issues")
public class IssueController {

    @Autowired
    private IssueService service;

    @GetMapping("/test")
    public IssueDto testGet(){
        return new IssueDto();
    }

    @GetMapping
    public List<IssueDto> getAll(){
        return service.findAll();
    }

    @PostMapping
    public IssueDto addIssue(IssueDto dto){
        return service.addIssue(dto);
    }

}
