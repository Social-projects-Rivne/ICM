package ua.softserve.rv_028.issuecitymonitor.service;

public interface RestorePasswordService {
    boolean createOrderRestorePassword(String email);
    void setNewPasswordForUser(String email, String password, String token);
}
