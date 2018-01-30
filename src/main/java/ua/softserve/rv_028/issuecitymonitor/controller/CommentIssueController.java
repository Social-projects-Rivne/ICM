package ua.softserve.rv_028.issuecitymonitor.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.softserve.rv_028.issuecitymonitor.entity.CommentIssue;
import ua.softserve.rv_028.issuecitymonitor.service.CommentService;

@RestController
@RequestMapping("/api/issues/comment")
@Log4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CommentController {

    CommentService commentService;

    @PostMapping
    public CommentIssue createComment(@RequestBody CommentIssue comment){
        log.debug("create comment");
        return commentService.createComment(comment);
    }
}
