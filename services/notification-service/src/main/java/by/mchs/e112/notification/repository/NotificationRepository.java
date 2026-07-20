package by.mchs.e112.notification.repository;

import by.mchs.e112.notification.domain.Notification;
import by.mchs.e112.notification.domain.NotificationStatus;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {

    List<Notification> findByStatus(NotificationStatus status);

    Page<Notification> findByRelatedEntityId(String relatedEntityId, Pageable pageable);
}
