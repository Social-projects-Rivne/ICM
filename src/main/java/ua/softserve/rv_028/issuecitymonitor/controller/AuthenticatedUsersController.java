package ua.softserve.rv_028.issuecitymonitor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ua.softserve.rv_028.issuecitymonitor.dto.UserDto;
import ua.softserve.rv_028.issuecitymonitor.entity.User;
import ua.softserve.rv_028.issuecitymonitor.service.MapperService;
import ua.softserve.rv_028.issuecitymonitor.service.UserProfileService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Map;

@RestController
public class AuthenticatedUsersController {

    private MapperService mapperService;
    private UserProfileService profileService;

    @Autowired
    public AuthenticatedUsersController(MapperService mapperService, UserProfileService profileService) {
        this.mapperService = mapperService;
        this.profileService = profileService;
    }

    @GetMapping(value = "/api/user-name")
    public String adminName(){
        Authentication userAuth = SecurityContextHolder.getContext().getAuthentication();
        return userAuth.getName();
    }

    @GetMapping(value = "/api/authority")
    public Collection<? extends GrantedAuthority> authority(){
        Authentication userAuth = SecurityContextHolder.getContext().getAuthentication();
        return userAuth.getAuthorities();
    }

    @GetMapping(value = "/api/details")
    public UserDto userDetails(){
        Authentication userAuth = SecurityContextHolder.getContext().getAuthentication();
        if (!userAuth.getName().equals("anonymousUser"))
            return mapperService.fromEntityToDto((User) userAuth.getPrincipal());
        else
            return new UserDto();
    }

    @GetMapping(value = "/api/userDetails")
    public Map getUserInfo(){
        Authentication userAuth = SecurityContextHolder.getContext().getAuthentication();
        return profileService.getUserInfo(userAuth.getName());
    }

    @PostMapping(value = "/api/userSetting/updatePassword")
    public void updatePassword(@RequestParam String email, @RequestParam String oldPassword,
                               @RequestParam String newPassword){
        profileService.updatePassword(email, oldPassword, newPassword);
    }

    @PostMapping(value = "/api/userSetting/updateContacts")
    public void updateContactInfo(@RequestParam String email, @RequestParam(required = false) String firstName,
                                  @RequestParam(required = false) String lastName, @RequestParam(required = false) String phone){
        profileService.updateContactsInfo(email, firstName, lastName, phone);
    }

    @PostMapping(value = "/api/userSettings/updateLogo")
    public void updatePortfolioPhoto(@RequestParam MultipartFile photo){
        profileService.updatePortfolioPhoto(photo, "admin@mail.com");
    }

    @PostMapping(value = "/api/image", produces = "image/png")
    public byte[] downloadImage(@RequestParam String image) throws IOException {
        return Files.readAllBytes(Paths.get("src/main/resources/users/logo/1151/", image));
    }
}

