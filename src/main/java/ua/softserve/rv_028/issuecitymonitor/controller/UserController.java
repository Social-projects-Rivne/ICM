package ua.softserve.rv_028.issuecitymonitor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ua.softserve.rv_028.issuecitymonitor.dto.UserDto;
import ua.softserve.rv_028.issuecitymonitor.entity.User;
import ua.softserve.rv_028.issuecitymonitor.service.UserService;

import java.util.List;
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

    @GetMapping("/test")
    public UserDto testGet(){
        return new UserDto();
    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        LOG.info("Get all users.");
        return new ResponseEntity<Object>(service.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public UserDto addUser(UserDto dto){
        return service.addUser(dto);
    }
}
