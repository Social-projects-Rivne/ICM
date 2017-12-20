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

import java.util.logging.Logger;


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
    public ResponseEntity<?> deleteUser(@PathVariable long id ){
        service.deleteById(id);
        if (UserService.isMessages()){
            LOG.info("User is deleted and map show it!");
            return new ResponseEntity<>( HttpStatus.OK);
        } else
            return new ResponseEntity<>( HttpStatus.NOT_FOUND);

    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        LOG.info("Get all users.");
        return new ResponseEntity<Object>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public UserDto getOne(@PathVariable long id){
        LOG.info("User is founded!");
        return service.findByID(id);
    }

    @PutMapping("/{id}")
    public String updateForUser(@RequestBody UserDto userDto) {
        LOG.info("OK");
        return service.updateUser(userDto);
    }




}
