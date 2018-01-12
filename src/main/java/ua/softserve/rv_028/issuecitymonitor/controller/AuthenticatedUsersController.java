package ua.softserve.rv_028.issuecitymonitor.controller;

import org.apache.log4j.Logger;
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
    private static final Logger LOGGER = Logger.getLogger(AuthenticatedUsersController.class);

    @Autowired
    public AuthenticatedUsersController(UserProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping(value = "/api/userName")
    public String getUserName(){
        Authentication userAuth = SecurityContextHolder.getContext().getAuthentication();
        return userAuth.getName();
    }

    @GetMapping(value = "/api/authority")
    public Collection<? extends GrantedAuthority> getUserAuthority(){
        Authentication userAuth = SecurityContextHolder.getContext().getAuthentication();
        LOGGER.debug("Get " + userAuth.getName() + " authorities");
        return userAuth.getAuthorities();
    }

    @GetMapping(value = "/api/userDetails")
    public Map getUserInfo(){
        Authentication userAuth = SecurityContextHolder.getContext().getAuthentication();
        LOGGER.debug("Get " + userAuth.getName() + " details");
        return profileService.getUserInfo(userAuth.getName());
    }

    @PostMapping(value = "/api/userSetting/updatePassword")
    public void updatePassword(@RequestParam String oldPassword, @RequestParam String newPassword){
        Authentication userAuth = SecurityContextHolder.getContext().getAuthentication();
        LOGGER.debug("Restore password request for: " + userAuth.getName());
        profileService.updatePassword(userAuth.getName(), oldPassword, newPassword);
    }

    @PostMapping(value = "/api/userSetting/updateContacts")
    public void updateContactInfo(@RequestParam(required = false) String firstName,
                                  @RequestParam(required = false) String lastName,
                                  @RequestParam(required = false) String phone){
        Authentication userAuth = SecurityContextHolder.getContext().getAuthentication();
        LOGGER.debug("Update contacts request for: " + userAuth.getName());
        profileService.updateContactsInfo(userAuth.getName(), firstName, lastName, phone);
    }

    @PostMapping(value = "/api/userSettings/updateLogo")
    public void updatePortfolioPhoto(@RequestParam MultipartFile photo){
        Authentication userAuth = SecurityContextHolder.getContext().getAuthentication();
        LOGGER.debug("Update photo request for: " + userAuth.getName());
        profileService.updatePortfolioPhoto(photo, userAuth.getName());
    }

    @GetMapping(value = "/api/avatar/original/{id}", produces = "image/png")
    public byte[] downloadAvatar(@PathVariable(name = "id") String id) throws IOException {
        LOGGER.debug("Get original avatar " + id);
        return profileService.getOriginalAvatar(Long.parseLong(id));
    }

    @GetMapping(value = "/api/avatar/small/{id}", produces = "image/png")
    public byte[] downloadSmallAvatar(@PathVariable(name = "id") String id) throws IOException {
        LOGGER.debug("Get small avatar " + id);
        return profileService.getSmallAvatar(Long.parseLong(id));
    }

    @GetMapping(value = "/api/avatar/medium/{id}", produces = "image/png")
    public byte[] downloadMediumAvatar(@PathVariable(name = "id") String id) throws IOException {
        LOGGER.debug("Get medium avatar " + id);
        return profileService.getMediumAvatar(Long.parseLong(id));
    }
}

