package ua.softserve.rv_028.issuecitymonitor.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ua.softserve.rv_028.issuecitymonitor.controller.AdviceController;
import ua.softserve.rv_028.issuecitymonitor.dto.UserDto;

import javax.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl extends Thread implements EmailService{

    private static final Logger LOGGER = Logger.getLogger(AdviceController.class.getName());

    private String receiverEmail;
    private String emailSubject;
    private String emailText;

    @Autowired
    public JavaMailSender emailSender;

    @Override
    public void run() {
        super.run();
        sendEmail();
    }

    @Override
    public void sendRestorePasswordEmail(UserDto user, String token) {
        this.receiverEmail = user.getEmail();
        this.emailSubject = "Restore password";
        this.emailText = templateRestorePassword(user.getFirstName(), user.getLastName(), token);
        this.start();
    }

    @Override
    public void sendEmail(UserDto user, String subject, String text) {
        this.receiverEmail = user.getEmail();
        this.emailSubject = subject;
        this.emailText = text;
    }

    @Override
    public void sendGreetingEmail(String emailUser, String firstName, String lastName){
        this.receiverEmail = emailUser;
        this.emailSubject = "ICM registration";
        this.emailText = "Dear " + firstName + " " + lastName + ".\nYou have been successfully registered!";
        this.start();
    }

    private void sendEmail(){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(receiverEmail);
        message.setSubject(emailSubject);
        message.setText(emailText);


        LOGGER.info("Sending " + emailSubject + " email to " + receiverEmail + " ...");
        emailSender.send(message);
    }

    private String templateRestorePassword(String firstName, String lastName, String token){
        return "Dear " + firstName + " " + lastName + ".\nSomeone has requested a link to change your password. " +
                "You can do this through the link below.\n" + token;
    }
}
