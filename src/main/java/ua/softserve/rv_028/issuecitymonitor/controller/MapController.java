package ua.softserve.rv_028.issuecitymonitor.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/map")
@Log4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MapController {

    @Value("${value.from.file}")
    private String pathForImg;

    @GetMapping("/img")
    public String getPath(){
        log.debug("GET request for images path");
        return pathForImg;
    }


}
