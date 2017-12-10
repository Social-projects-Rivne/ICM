package ua.softserve.rv_028.issuecitymonitor.controller;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class AdviceController {

    private static final Logger LOG = Logger.getLogger(AdviceController.class.getName());

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(IllegalStateException.class)
    public void issueNotFound(Exception e){
        LOG.error(e.getMessage());
    }
}