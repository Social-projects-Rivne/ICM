package ua.softserve.rv_028.issuecitymonitor.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ua.softserve.rv_028.issuecitymonitor.dto.UserDto;
import ua.softserve.rv_028.issuecitymonitor.service.UserService;

import static ua.softserve.rv_028.issuecitymonitor.Constants.PAGE_SIZE;

@RestController
@RequestMapping("api/users")
public class UserController {
    private static final Logger LOGGER = Logger.getLogger(UserController.class);

    private final UserService service;

    @Autowired
    public UserController(UserService service){
        this.service = service;
    }

    @GetMapping

    public Page<UserDto> getAllByPage(@RequestParam(value = "page", defaultValue = "1") int page,
                                      @RequestParam(value = "size", defaultValue = (""+PAGE_SIZE)) int size,
                                      @RequestParam(value = "deleted", defaultValue = "false") boolean isDeleted){
        LOGGER.debug("GET request for all users by page");
        return service.findAllByPage(page, size, isDeleted);
    }

    @GetMapping("/{id}")
    public UserDto getOne(@PathVariable long id){
        LOGGER.debug("GET request");
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public UserDto update(@RequestBody UserDto userDto) {
        LOGGER.debug("PUT request");
        return service.update(userDto);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable long id ){
        LOGGER.debug("DELETE request");
        service.deleteById(id);
    }

}
