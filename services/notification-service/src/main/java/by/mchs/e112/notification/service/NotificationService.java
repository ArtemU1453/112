package by.mchs.e112.notification.service;

import by.mchs.e112.notification.domain.Notification;
import by.mchs.e112.notification.domain.NotificationChannel;
import by.mchs.e112.notification.dto.NotificationRequest;
import by.mchs.e112.notification.dto.NotificationResponse;
import by.mchs.e112.notification.repository.NotificationRepository;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NotificationService {

    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);

    private final NotificationRepository repository;
    private final Map<NotificationChannel, ChannelSender> senders = new EnumMap<>(NotificationChannel.class);

    public NotificationService(NotificationRepository repository, List<ChannelSender> channelSenders) {
        this.repository = repository;
        channelSenders.forEach(sender -> senders.put(sender.channel(), sender));
    }

    @Transactional
    public NotificationResponse send(NotificationRequest request) {
        Notification notification = new Notification(UUID.randomUUID(), request.channel(),
            request.recipient(), request.subject(), request.body(), request.relatedEntityId());
        deliver(notification);
        repository.save(notification);
        return toResponse(notification);
    }

    @Transactional(readOnly = true)
    public NotificationResponse getById(UUID id) {
        return repository.findById(id).map(this::toResponse)
            .orElseThrow(() -> new IllegalArgumentException("Уведомление %s не найдено".formatted(id)));
    }

    @Transactional(readOnly = true)
    public Page<NotificationResponse> getByEntity(String entityId, Pageable pageable) {
        return repository.findByRelatedEntityId(entityId, pageable).map(this::toResponse);
    }

    private void deliver(Notification notification) {
        ChannelSender sender = senders.get(notification.getChannel());
        if (sender == null) {
            notification.markFailed("Канал не поддерживается: " + notification.getChannel());
            return;
        }
        try {
            sender.send(notification);
            notification.markSent();
        } catch (Exception ex) {
            log.error("Ошибка отправки уведомления {} по каналу {}", notification.getId(),
                notification.getChannel(), ex);
            notification.markFailed(ex.getMessage());
        }
    }

    private NotificationResponse toResponse(Notification n) {
        return new NotificationResponse(n.getId(), n.getChannel().name(), n.getRecipient(),
            n.getSubject(), n.getBody(), n.getStatus().name(), n.getRelatedEntityId(),
            n.getErrorMessage(), n.getCreatedAt(), n.getSentAt());
    }
}
