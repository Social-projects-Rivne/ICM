package ua.softserve.rv_028.issuecitymonitor.service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserve.rv_028.issuecitymonitor.dao.CommentDao;

@Service
@Log4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CommentService {

    CommentDao commentDao;
}
