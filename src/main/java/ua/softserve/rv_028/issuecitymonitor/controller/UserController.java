package ua.softserve.rv_028.issuecitymonitor.controller;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import ua.softserve.rv_028.issuecitymonitor.dto.UserDto;
import ua.softserve.rv_028.issuecitymonitor.service.UserService;

import static ua.softserve.rv_028.issuecitymonitor.Constants.PAGE_SIZE;

@RestController
@RequestMapping("api/users")
@Log4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserController {

    UserService service;

    @GetMapping

    public Page<UserDto> getAllByPage(@RequestParam(value = "page", defaultValue = "1") int page,
                                      @RequestParam(value = "size", defaultValue = (""+PAGE_SIZE)) int size,
                                      @RequestParam(value = "direction", defaultValue = "ASC") Sort.Direction direction,
                                      @RequestParam(value = "sort", defaultValue = "id") String sort,
                                      @RequestParam(value = "deleted", defaultValue = "false") boolean isDeleted){
        log.debug("GET request for all users by page");
        return service.findAllByPage(page, size, direction, sort, isDeleted);
    }

    @GetMapping("/{id}")
    public UserDto getOne(@PathVariable long id){
        log.debug("GET request");
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public UserDto update(@RequestBody UserDto userDto) {
        log.debug("PUT request");
        return service.update(userDto);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable long id ){
        log.debug("DELETE request");
        service.deleteById(id);
    }

}
