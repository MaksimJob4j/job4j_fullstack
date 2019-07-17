package ru.job4j.notification.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.job4j.notification.domain.Notification;

import javax.mail.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;

@Service
public class MailService {
    private final JavaMailSender sender;

    @Value("${spring.mail.username}")
    private String user;
    @Value("${spring.mail.password}")
    private String password;
    @Value("${spring.mail.pop3.host}")
    private String pop3Host;

    public MailService(JavaMailSender sender) {
        this.sender = sender;
    }

    public List<Notification> receive() throws IOException, MessagingException {
        final var msgs = new ArrayList<Notification>();
        Properties properties = new Properties();
        properties.put("mail.pop3.host", this.pop3Host);
        properties.put("mail.pop3.port", "995");
        properties.put("mail.pop3.starttls.enable", "true");
        Session emailSession = Session.getDefaultInstance(properties);
        Store store = emailSession.getStore("pop3s");
        store.connect(this.pop3Host, this.user, this.password);
        Folder inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_ONLY);
        for (var msg : inbox.getMessages()) {
            msgs.add(new Notification(
                    null,
                    msg.getFrom()[0].toString(),
                    msg.getSubject(),
                    msg.getContent().toString(),
                    msg.getSentDate()));
        }
        inbox.close(false);
        store.close();
        msgs.sort(Comparator.comparing(Notification::getDate).reversed());
        return msgs;
    }

    public void send(Notification notification) throws MailException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(notification.getTo());
        message.setSubject(notification.getSubject());
        message.setText(notification.getBody());
        this.sender.send(message);
    }
}
