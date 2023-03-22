package be.domain.notice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import be.domain.notice.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
	List<Notification> findAllByUserId(Long userId);
}
