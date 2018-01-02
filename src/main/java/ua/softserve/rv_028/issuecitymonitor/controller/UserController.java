package ua.softserve.rv_028.issuecitymonitor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import ua.softserve.rv_028.issuecitymonitor.dto.UserDto;
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
    public Page<UserDto> getAllByPage(@RequestParam(value = "page", defaultValue = "1") int page,
                                       @RequestParam(value = "size", defaultValue = "10") int size){
        LOG.debug("GET request for all users by page");
        return service.findAllByPage(new PageRequest(page-1, size));
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
