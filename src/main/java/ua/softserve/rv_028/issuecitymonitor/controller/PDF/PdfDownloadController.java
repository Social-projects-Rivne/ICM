package ua.softserve.rv_028.issuecitymonitor.controller.PDF;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


@RestController
@RequestMapping("/api")
public class PdfDownloadController {
    @Autowired
    ServletContext context;

    @Value("${path.for.download}")
    private String downloadPath;
    @GetMapping(value = "/pdf/{fileName:.+}", produces = "application/pdf")
    public ResponseEntity<InputStreamResource> download(@PathVariable("fileName") String fileName) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        headers.add("Access-Control-Allow-Origin", "*");
        headers.add("Access-Control-Allow-Methods", "GET, POST, PUT");
        headers.add("Access-Control-Allow-Headers", "Content-Type");
        headers.add("Content-Disposition", "filename=" + fileName);
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        InputStreamResource inputStreamResource = new InputStreamResource(new FileInputStream(new File(downloadPath, fileName)));
        ResponseEntity<InputStreamResource> response = new ResponseEntity<InputStreamResource>(
                inputStreamResource, headers, HttpStatus.OK);
        return response;
    }


}