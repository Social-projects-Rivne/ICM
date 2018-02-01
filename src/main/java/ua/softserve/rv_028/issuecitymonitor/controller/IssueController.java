package ua.softserve.rv_028.issuecitymonitor.controller;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import ua.softserve.rv_028.issuecitymonitor.dto.IssueDto;
import ua.softserve.rv_028.issuecitymonitor.dto.IssueLocationDto;
import ua.softserve.rv_028.issuecitymonitor.service.IssueService;

import static ua.softserve.rv_028.issuecitymonitor.Constants.PAGE_SIZE;

import java.util.List;

@RestController
@RequestMapping("/api/issues")
@Log4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class IssueController {

    IssueService issueService;

    @GetMapping("/{id}")
    public IssueDto getOne(@PathVariable long id){
        log.debug("GET request");
        return issueService.findById(id);
    }

    @GetMapping("/map")
    public List<IssueLocationDto> getAll(){
        log.debug("GET request");
        return issueService.findAll();
    }

    @GetMapping
    public Page<IssueDto> getAllByPage(@RequestParam(value = "page", defaultValue = "1") int page,
                                       @RequestParam(value = "size", defaultValue = (""+PAGE_SIZE)) int size,
                                       @RequestParam(value = "direction", defaultValue = "ASC") Sort.Direction direction,
                                       @RequestParam(value = "sort", defaultValue = "id") String sort){
        log.debug("GET request for all issues by page");
        return issueService.findAllByPage(page, size, direction, sort);
    }

    @PostMapping
    public IssueDto addIssue(@RequestBody IssueDto dto){
        log.debug("POST request");
        return issueService.addIssue(dto);
    }

    @PutMapping("/{id}")
    public IssueDto editIssue(@RequestBody IssueDto dto){
        log.debug("PUT request");
        return issueService.update(dto);
    }

    @DeleteMapping("/{id}")
    public void deleteIssue(@PathVariable long id) {
        log.debug("DELETE request");
        issueService.deleteById(id);
    }

}
