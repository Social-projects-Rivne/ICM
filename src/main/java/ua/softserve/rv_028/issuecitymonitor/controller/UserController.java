package ua.softserve.rv_028.issuecitymonitor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ua.softserve.rv_028.issuecitymonitor.dto.UserDto;
import ua.softserve.rv_028.issuecitymonitor.exception.UserNotFoundException;
import ua.softserve.rv_028.issuecitymonitor.service.UserServiceImpl;

import java.util.logging.Logger;


@RestController
@RequestMapping("api/users")
public class UserController {
    private static final Logger LOG = Logger.getLogger(UserController.class.getName());

    private final UserServiceImpl service;

    @Autowired
    public UserController(UserServiceImpl service){
        this.service = service;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable long id ){
        try{
            service.deleteById(id);
            return new ResponseEntity<>( HttpStatus.OK);
        }catch (UserNotFoundException e){
            return new ResponseEntity<>( HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        LOG.info("Get all users.");
        return new ResponseEntity<Object>(service.findAll(), HttpStatus.OK);
    }


    @PostMapping("/{id}")
    public ResponseEntity<?> updateForUser(@PathVariable(value = "id") long id, @RequestBody UserDto userDto){
        try {
            UserDto updateUser = service.updateUser(userDto);
            return new ResponseEntity<Object>(updateUser, HttpStatus.OK);
        }catch (UserNotFoundException e)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/add")
    public UserDto addUser(@RequestBody UserDto dto){
        return service.addUser(dto);

    }
}
