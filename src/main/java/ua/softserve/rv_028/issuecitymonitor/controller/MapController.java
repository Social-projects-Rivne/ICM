package ua.softserve.rv_028.issuecitymonitor.controller;

import com.google.common.io.ByteStreams;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.softserve.rv_028.issuecitymonitor.dto.IssueDto;
import ua.softserve.rv_028.issuecitymonitor.entity.Issue;
import ua.softserve.rv_028.issuecitymonitor.service.IssueService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
    public ResponseEntity<byte[]> getImageAsResponseEntity() throws IOException{
        HttpHeaders headers = new HttpHeaders();
        byte[] media = Files.readAllBytes(Paths.get("C:/Users/Kolia/Desktop/Image/first_img.jpg"));
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());

        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(media, headers, HttpStatus.OK);
        return responseEntity;
    }
}



