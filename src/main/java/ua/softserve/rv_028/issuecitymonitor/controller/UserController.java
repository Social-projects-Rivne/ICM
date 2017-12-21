package ua.softserve.rv_028.issuecitymonitor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import ua.softserve.rv_028.issuecitymonitor.dto.UserDto;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.UserRole;
import ua.softserve.rv_028.issuecitymonitor.service.UserService;

import org.apache.log4j.Logger;



@RestController
@RequestMapping("api/users")
public class UserController {
    private static final Logger LOG = Logger.getLogger(UserController.class.getName());

    private final UserService service;

    @Autowired
    public UserController(UserService service){
        this.service = service;
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable long id ){
        service.deleteById(id);
    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        LOG.debug("Get all users.");
        return new ResponseEntity<Object>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public UserDto getOne(@PathVariable long id){
        LOG.debug("User is founded!");
        return service.findByID(id);
    }

    @PutMapping("/{id}")
    public UserDto updateForUser(@RequestBody UserDto userDto) {
        LOG.debug("Put request for user update!");
        return service.updateUser(userDto);
    }




}
