package ua.softserve.rv_028.issuecitymonitor.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.softserve.rv_028.issuecitymonitor.dto.IssueDto;
import ua.softserve.rv_028.issuecitymonitor.service.IssueService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


@RestController
@RequestMapping("/api/map")
@Log4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MapController {


    private final IssueService issueService;
    private IssueDto issueDto;

    @Value("${value.from.file}")
    private String pathForImg;

    @GetMapping("/img")
    public String getPath(){
        log.debug("GET request for images path");
        return pathForImg;
    }

    @GetMapping(value = "/img/{id}", produces = "image/png")
    public ResponseEntity<byte[]> getImageAsResponseEntity(@PathVariable long id) throws IOException{
        issueDto = issueService.findById(id);
        HttpHeaders headers = new HttpHeaders();
        byte[] media = Files.readAllBytes(Paths.get(pathForImg + "/" + issueDto.getPhoto()));
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());

        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(media, headers, HttpStatus.OK);
        return responseEntity;
    }
}



