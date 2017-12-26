package ua.softserve.rv_028.issuecitymonitor.service;

public interface UserProfileService {
    void setNewPassword(String email, String oldPassword, String newPassword);
}
