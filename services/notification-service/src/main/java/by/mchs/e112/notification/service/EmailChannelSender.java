package by.mchs.e112.notification.service;

import by.mchs.e112.notification.domain.Notification;
import by.mchs.e112.notification.domain.NotificationChannel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailChannelSender implements ChannelSender {

    private final JavaMailSender mailSender;
    private final String from;

    public EmailChannelSender(JavaMailSender mailSender,
                              @Value("${app.mail.from:no-reply@112.by}") String from) {
        this.mailSender = mailSender;
        this.from = from;
    }

    @Override
    public NotificationChannel channel() {
        return NotificationChannel.EMAIL;
    }

    @Override
    public void send(Notification notification) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(notification.getRecipient());
        message.setSubject(notification.getSubject() == null ? "Система-112" : notification.getSubject());
        message.setText(notification.getBody());
        mailSender.send(message);
    }
}
