package ua.softserve.rv_028.issuecitymonitor.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.softserve.rv_028.issuecitymonitor.dto.PetitionDto;
import ua.softserve.rv_028.issuecitymonitor.service.PetitionService;

import java.util.List;

@RestController
@RequestMapping("/api/petitions")
public class PetitionController {

    private static final Logger LOGGER = Logger.getLogger(PetitionController.class.getName());

    private final PetitionService petitionService;

    @Autowired
    public PetitionController(PetitionService petitionService) {
        this.petitionService = petitionService;
    }

    @GetMapping
    public List<PetitionDto> getAll(){
        LOGGER.debug("GET request for all users");
        return petitionService.findAll();
    }

    @GetMapping(value = "/{id}")
    public PetitionDto getOne(@PathVariable long id){
        LOGGER.debug("GET request");
        PetitionDto result = petitionService.findById(id);
        LOGGER.debug("GET request successful");
        return result;
    }

    @PutMapping("/{id}")
    public PetitionDto update(@PathVariable long id, @RequestBody PetitionDto petitionDto){
        LOGGER.debug("PUT request");
        PetitionDto result = petitionService.update(petitionDto);
        LOGGER.debug("PUT request successful");
        return result;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id){
        LOGGER.debug("DELETE request");
        petitionService.deleteById(id);
        LOGGER.debug("DELETE request successful");
    }
}
