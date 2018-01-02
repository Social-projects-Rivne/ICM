package ua.softserve.rv_028.issuecitymonitor.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.softserve.rv_028.issuecitymonitor.dto.UserDto;
import ua.softserve.rv_028.issuecitymonitor.service.UserService;

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
    public ResponseEntity<?> getAll(){
        LOGGER.debug("GET request for all users");
        return new ResponseEntity<Object>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public UserDto getOne(@PathVariable long id){
        LOGGER.debug("GET request");
        return service.findByID(id);
    }

    @PutMapping("/{id}")
    public UserDto update(@RequestBody UserDto userDto) {
        LOGGER.debug("PUT request");
        return service.updateUser(userDto);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable long id ){
        LOGGER.debug("DELETE request");
        service.deleteById(id);
    }

}
