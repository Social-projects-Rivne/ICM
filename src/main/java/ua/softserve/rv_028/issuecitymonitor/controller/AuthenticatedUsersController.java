package ua.softserve.rv_028.issuecitymonitor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.softserve.rv_028.issuecitymonitor.service.UserProfileService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Map;

@RestController
public class AuthenticatedUsersController {

    private UserProfileService profileService;

    @Autowired
    public AuthenticatedUsersController(UserProfileService profileService) {
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

    @GetMapping(value = "/api/userDetails")
    public Map getUserInfo(){
        Authentication userAuth = SecurityContextHolder.getContext().getAuthentication();
        return profileService.getUserInfo(userAuth.getName());
    }

    @PostMapping(value = "/api/userSetting/updatePassword")
    public void updatePassword(@RequestParam String oldPassword, @RequestParam String newPassword){
        Authentication userAuth = SecurityContextHolder.getContext().getAuthentication();
        profileService.updatePassword(userAuth.getName(), oldPassword, newPassword);
    }

    @PostMapping(value = "/api/userSetting/updateContacts")
    public void updateContactInfo(@RequestParam(required = false) String firstName,
                                  @RequestParam(required = false) String lastName,
                                  @RequestParam(required = false) String phone){
        Authentication userAuth = SecurityContextHolder.getContext().getAuthentication();
        profileService.updateContactsInfo(userAuth.getName(), firstName, lastName, phone);
    }

    @PostMapping(value = "/api/userSettings/updateLogo")
    public void updatePortfolioPhoto(@RequestParam MultipartFile photo){
        Authentication userAuth = SecurityContextHolder.getContext().getAuthentication();
        profileService.updatePortfolioPhoto(photo, userAuth.getName());
    }

    @GetMapping(value = "/api/avatar/{id}", produces = "image/png")
    public byte[] downloadAvatar(@PathVariable(name = "id") String id) throws IOException {
        return Files.readAllBytes(Paths.get("src/main/resources/users/logo/", id, "medium.png"));
    }
}

