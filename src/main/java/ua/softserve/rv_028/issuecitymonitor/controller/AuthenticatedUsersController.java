package ua.softserve.rv_028.issuecitymonitor.controller;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.softserve.rv_028.issuecitymonitor.dto.UserDto;
import ua.softserve.rv_028.issuecitymonitor.entity.User;
import ua.softserve.rv_028.issuecitymonitor.service.MapperService;
import ua.softserve.rv_028.issuecitymonitor.service.UserProfileService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
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

    @PostMapping(value = "/api/image")
    public ResponseEntity<byte[]> downloadImage(@RequestParam String image, HttpServletResponse response) throws IOException {

        HttpHeaders headers = new HttpHeaders();
        byte[] media = Files.readAllBytes(Paths.get("src/main/resources/users/logo/1151/", image));

        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(media, headers, HttpStatus.OK);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        return responseEntity;

    }

    @Autowired
    ServletContext servletContext;

    @RequestMapping(value = "/image-manual-response", method = RequestMethod.GET)
    public void getImageAsByteArray(HttpServletResponse response) throws IOException {
        InputStream in = servletContext.getResourceAsStream("/users/logo/1151/original");
//        URL resource = getClass().getClassLoader().getResource("users/logo/1151/original.png");
        response.setContentType(MediaType.IMAGE_PNG_VALUE);
        IOUtils.copy(in, response.getOutputStream());
    }

    @PostMapping(value = "/image-manual-response1")
    public void getImageAsByteArray(HttpServletResponse response, @RequestParam String image) throws IOException {
        InputStream in = servletContext.getResourceAsStream("resources/users/logo/1151/"  + image);
//        URL resource = getClass().getClassLoader().getResource("users/logo/1151/original.png");
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        IOUtils.copy(in, response.getOutputStream());
    }


}

