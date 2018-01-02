package ua.softserve.rv_028.issuecitymonitor.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import ua.softserve.rv_028.issuecitymonitor.dto.PetitionDto;
import ua.softserve.rv_028.issuecitymonitor.service.PetitionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ua.softserve.rv_028.issuecitymonitor.service.PetitionServiceImpl;

@RestController
@RequestMapping("/api/petitions")
public class PetitionController {


    @Autowired
    private PetitionServiceImpl service;

    private static final Logger LOGGER = Logger.getLogger(PetitionController.class.getName());

    private final PetitionService petitionService;

    @Autowired
    public PetitionController(PetitionService petitionService) {
        this.petitionService = petitionService;
    }

    @GetMapping
    public Page<PetitionDto> getAllByPage(@RequestParam(value = "page", defaultValue = "1") int page,
                                       @RequestParam(value = "size", defaultValue = "10") int size){
        LOGGER.debug("GET request for all petitions by page");
        return petitionService.findAllByPage(new PageRequest(page-1, size));
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


    @PostMapping(path = "/add")
    public void createPetition(@RequestBody PetitionDto petitionDto) {

        petitionService.addPetition(petitionDto);
    }

}
