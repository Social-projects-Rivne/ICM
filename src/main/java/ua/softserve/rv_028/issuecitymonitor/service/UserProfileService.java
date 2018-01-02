package ua.softserve.rv_028.issuecitymonitor.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface UserProfileService {
    void updatePassword(String email, String oldPassword, String newPassword);
    void updateContactsInfo(String email, String fistName, String lastName, String phone);
    Map getUserInfo(String email);
    void updatePortfolioPhoto(MultipartFile photo);
}
