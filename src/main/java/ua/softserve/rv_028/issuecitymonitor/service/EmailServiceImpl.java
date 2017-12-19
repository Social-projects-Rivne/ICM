package ua.softserve.rv_028.issuecitymonitor.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl extends Thread implements EmailService{

    private static final Logger LOGGER = Logger.getLogger(EmailServiceImpl.class.getName());

    private String receiverEmail;
    private String emailSubject;
    private String emailText;

    private final JavaMailSender emailSender;

    @Autowired
    public EmailServiceImpl(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Override
    public void run() {
        super.run();
        sendGreetingEmail();
    }

    @Override
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

        LOGGER.info("Sending greeting email to "+ receiverEmail + " ...");
        emailSender.send(message);
    }
}
