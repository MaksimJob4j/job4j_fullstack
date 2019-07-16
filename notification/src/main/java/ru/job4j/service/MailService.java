package ru.job4j.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.job4j.domain.Notification;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;

@Service
public class MailService {

    @Value("${mail.username}")
    private String user;
    @Value("${mail.password}")
    private String password;
    @Value("${mail.pop3.host}")
    private String pop3Host;
    @Value("${mail.smtp.host}")
    private String smtpHost;

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

    public void send(Notification notification) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.host", this.smtpHost);
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        Session session = Session.getInstance(
                props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(user, password);
                    }
                }
        );
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(this.user));
        message.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(notification.getTo())
        );
        message.setSubject(notification.getSubject());
        message.setText(notification.getBody());
        Transport.send(message);
    }
}
