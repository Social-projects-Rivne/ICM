package ua.softserve.rv_028.issuecitymonitor.controller.PDF;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class PdfDownloadController {
    @Autowired
    ServletContext context;

    @Value("${path.for.download}")
    private String downloadPath;
    @GetMapping(value = "/pdf/{fileName:.+}", produces = "application/pdf")
    public ResponseEntity<InputStreamResource> download(@PathVariable("fileName") String fileName) throws IOException {
        FileReader pdfFile = new FileReader(downloadPath + fileName);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        headers.add("Access-Control-Allow-Origin", "*");
        headers.add("Access-Control-Allow-Methods", "GET, POST, PUT");
        headers.add("Access-Control-Allow-Headers", "Content-Type");
        headers.add("Content-Disposition", "filename=" + fileName);
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        InputStreamResource inputStreamResource = new InputStreamResource(new FileInputStream(downloadPath + fileName));
        ResponseEntity<InputStreamResource> response = new ResponseEntity<InputStreamResource>(
                inputStreamResource, headers, HttpStatus.OK);
        return response;
    }


}