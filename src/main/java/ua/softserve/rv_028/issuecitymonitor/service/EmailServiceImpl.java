package ua.softserve.rv_028.issuecitymonitor.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import ua.softserve.rv_028.issuecitymonitor.dto.UserDto;

@Service
public class EmailServiceImpl implements EmailService{

    private static final Logger LOGGER = Logger.getLogger(EmailServiceImpl.class.getName());

    private String receiverEmail;
    private String emailSubject;
    private String emailText;

    private JavaMailSender emailSender;

    @Autowired
    public EmailServiceImpl(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }


    @Async
    @Override
    public void sendEmail(UserDto user, String subject, String text) {
        this.receiverEmail = user.getEmail();
        this.emailSubject = subject;
        this.emailText = text;
        sendEmail();
    }

    @Async
    @Override
    public void sendRestorePasswordEmail(UserDto user, String token) {
        this.receiverEmail = user.getEmail();
        this.emailSubject = "Restore password";
        this.emailText = templateRestorePassword(user.getFirstName(), user.getLastName(), token);
        sendEmail();
    }

    @Async
    @Override
    public void sendGreetingEmail(String emailUser, String firstName, String lastName){
        this.receiverEmail = emailUser;
        this.emailSubject = "ICM registration";
        this.emailText = templateGreeting(firstName, lastName);
        sendEmail();
    }

    private void sendEmail(){
       MimeMessagePreparator message = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(receiverEmail);
            messageHelper.setSubject(emailSubject);
            messageHelper.setText(emailText, true);
        };

        try {
            LOGGER.info("Sending " + emailSubject + " email to " + receiverEmail + " ...");
            emailSender.send(message);
        } catch (MailAuthenticationException e){
            LOGGER.error(e);
        }
    }

    private String templateRestorePassword(String firstName, String lastName, String token){
        return "<html><body><p>Dear " + firstName + " " + lastName + ".</p><p>Someone has requested a link to change your password. " +
                "You can do this through the link below.</p>" + "<a>" + token + "</a></body></html>";
    }

    private String templateGreeting(String firstName, String lastName){
        return "<html><body><p>Dear " + firstName + " " + lastName + ".</p><p>You have been successfully registered!</p></body></html>";
    }
}
