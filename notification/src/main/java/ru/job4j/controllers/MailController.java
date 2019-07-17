package ru.job4j.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.job4j.domain.Notification;
import ru.job4j.service.MailService;

import javax.mail.MessagingException;
import java.io.IOException;

@Controller
@RequestMapping("/mail")
public class MailController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MailService.class);
    private final MailService mailService;

    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @GetMapping("/inbox")
    public String inbox(ModelMap model) {

        try {
            model.addAttribute("inbox_messages", this.mailService.receive());
        } catch (MessagingException | IOException e) {
            LOGGER.error(e.getMessage(), e);
            model.addAttribute("inboxError", e.getMessage());
        }
        return "inbox";
    }

    @GetMapping("/send")
    public String send() {
        return "sendMail";
    }

    @PostMapping("/send")
    public String send(@ModelAttribute("mail")Notification notification,
                     ModelMap model) {
        try {
            this.mailService.send(notification);
            model.addAttribute("sendMessage", "Mail sent successfully!");
        } catch (MailException e) {
            LOGGER.error(e.getMessage(), e);
            model.addAttribute("sendError", "Sending error!");
        }
        return "sendMail";
    }

}
