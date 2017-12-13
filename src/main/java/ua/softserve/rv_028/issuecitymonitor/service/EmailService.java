package ua.softserve.rv_028.issuecitymonitor.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ua.softserve.rv_028.issuecitymonitor.controller.AdviceController;

@Service
public class EmailService extends Thread{

    private static final Logger LOGGER = Logger.getLogger(AdviceController.class.getName());

    private String receiverEmail;
    private String emailSubject;
    private String emailText;

    @Autowired
    public JavaMailSender emailSender;

    @Override
    public void run() {
        super.run();
        sendGreetingEmail();
    }

    public void sendEmail(String emailUser, String firstName, String lastName){
        this.receiverEmail = emailUser;
        this.emailSubject = "ICM registration";
        this.emailText = "Dear " + firstName + " " + lastName + ".\nYou have been successfully registered!";
        this.start();
    }

    private void sendGreetingEmail(){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(receiverEmail);
        message.setSubject(emailSubject);
        message.setText(emailText);

        System.out.println("start sending email to "+ receiverEmail + "...");
        LOGGER.debug("start sending email to "+ receiverEmail + "...");
        emailSender.send(message);
    }
}
