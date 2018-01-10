package ua.softserve.rv_028.issuecitymonitor.controller;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pdf")
public class PdfController {

    private static final Logger LOGGER = Logger.getLogger(PetitionController.class.getName());

    @GetMapping(path = "/issues")
    private void downloadPdfIssues() {
        downloadPdf("issues");
    }


    @GetMapping(path = "/petitions")
    private void downloadPdfPetitions() {
        downloadPdf("petitions");
    }


    @GetMapping(path = "/events")
    private void downloadPdfEvents() {
        downloadPdf("events");
    }


    @GetMapping(path = "/users")
    private void downloadPdfUsers() {
        downloadPdf("users");
    }



    private void downloadPdf(String pdf){

        LOGGER.info(pdf + " request successful!");
    }

}
