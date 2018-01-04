package ua.softserve.rv_028.issuecitymonitor.controller;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.*;
import ua.softserve.rv_028.issuecitymonitor.dto.PetitionDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pdf")
public class PdfController {

    private static final Logger LOGGER = Logger.getLogger(PetitionController.class.getName());

    @GetMapping(path = "/issues")
    public void createPdfIssues() {
        LOGGER.info("Issues request successful");
    }


    @GetMapping(path = "/petitions")
    public void createPdPetition() {
        LOGGER.info("Petitions request successful");
    }


    @GetMapping(path = "/events")
    public void createPdEvents() {
        LOGGER.info("Events request successful");
    }


    @GetMapping(path = "/users")
    public void createPdUsers() {
        LOGGER.info("Users request successful");
    }

}
