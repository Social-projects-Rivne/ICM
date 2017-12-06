package ua.softserve.rv_028.issuecitymonitor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ua.softserve.rv_028.issuecitymonitor.dto.UserDto;
import ua.softserve.rv_028.issuecitymonitor.entity.User;
import ua.softserve.rv_028.issuecitymonitor.service.UserService;

import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {
    @Autowired
    private UserService service;

    @GetMapping("/test")
    public UserDto testGet(){
        return new UserDto();
    }

    @GetMapping
    public List<UserDto> getAll(){
        return service.findAll();
    }

    @PostMapping
    public UserDto addUser(UserDto dto){
        return service.addUser(dto);
    }
}
