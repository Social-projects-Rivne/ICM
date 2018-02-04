package ua.softserve.rv_028.issuecitymonitor.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.softserve.rv_028.issuecitymonitor.dto.CommentIssueDto;
import ua.softserve.rv_028.issuecitymonitor.dto.IssueDto;
import ua.softserve.rv_028.issuecitymonitor.service.CommentIssueService;

import java.util.List;

@RestController
@RequestMapping("/api/issues/{id}/comment")
@Log4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CommentIssueController {

    CommentIssueService commentIssueService;

    @GetMapping
    public List<CommentIssueDto> listComment(){
        log.debug("get list comments for issue");
        return commentIssueService.findAllComment();
    }

    @PostMapping
    public CommentIssueDto createComment(@RequestBody CommentIssueDto comment, @RequestBody IssueDto issue){
        log.debug("create comment");
        return commentIssueService.createComment(comment, issue);
    }
}
