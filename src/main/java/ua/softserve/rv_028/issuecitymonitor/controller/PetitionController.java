package ua.softserve.rv_028.issuecitymonitor.controller;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import ua.softserve.rv_028.issuecitymonitor.dto.PetitionDto;
import ua.softserve.rv_028.issuecitymonitor.service.PetitionService;

import static ua.softserve.rv_028.issuecitymonitor.Constants.PAGE_SIZE;

@RestController
@RequestMapping("/api/petitions")
@Log4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PetitionController {

    PetitionService petitionService;

    @GetMapping
    public Page<PetitionDto> getAllByPage(@RequestParam(value = "page", defaultValue = "1") int page,
                                          @RequestParam(value = "size", defaultValue = (""+PAGE_SIZE)) int size,
                                          @RequestParam(value = "direction", defaultValue = "ASC") Sort.Direction direction,
                                          @RequestParam(value = "sort", defaultValue = "id") String sort){
        log.debug("GET request for all petitions by page");
        return petitionService.findAllByPage(page, size, direction, sort);
    }

    @GetMapping(value = "/{id}")
    public PetitionDto getOne(@PathVariable long id){
        log.debug("GET request");
        return petitionService.findById(id);
    }

    @PutMapping("/{id}")
    public PetitionDto update(@PathVariable long id, @RequestBody PetitionDto petitionDto){
        log.debug("PUT request");
        return petitionService.update(petitionDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id){
        log.debug("DELETE request");
        petitionService.deleteById(id);
    }

    @PostMapping(path = "/add")
    public void createPetition(@RequestBody PetitionDto petitionDto) {
        log.debug("POST request");
        petitionService.addPetition(petitionDto);
    }

}
