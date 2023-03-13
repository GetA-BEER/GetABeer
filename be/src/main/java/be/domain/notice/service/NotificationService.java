package be.domain.notice.service;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import be.domain.notice.dto.NotificationDto;
import be.domain.notice.entity.Notification;
import be.domain.notice.entity.NotificationType;
import be.domain.notice.repository.EmitterRepository;
import be.domain.notice.repository.NotificationQRepository;
import be.domain.notice.repository.NotificationRepository;
import be.domain.user.entity.User;
import be.domain.user.service.UserService;
import be.global.exception.BusinessLogicException;
import be.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService {

	private static final Logger log = LoggerFactory.getLogger(NotificationService.class);
	private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;

	private final EntityManager em;
	private final UserService userService;
	private final EmitterRepository emitterRepository;
	private final NotificationRepository notificationRepository;
	private final NotificationQRepository notificationQRepository;

	// SSE 통신
	public SseEmitter subscribe(String lastEventId) {
		Long userId = userService.getLoginUser().getId();
		String id = userId + "_" + System.currentTimeMillis();
		SseEmitter emitter = emitterRepository.save(id, new SseEmitter(DEFAULT_TIMEOUT));

		emitter.onCompletion(() -> emitterRepository.deleteAllStartByWithId(id));
		emitter.onTimeout(() -> emitterRepository.deleteAllStartByWithId(id));

		// 503 에러를 방지 더미 데이터
		sendToClient(emitter, id, "(503에러 방지 더미데이터) SSE 연결됐습니당! [이거슨 유저아이디=" + userId + "]");

		// 미수신 Event 목록 존재 시 전송으로 Event 유실 방지
		if (!lastEventId.isEmpty()) {
			Map<String, SseEmitter> events = emitterRepository.findAllStartById(String.valueOf(userId));
			events.entrySet().stream()
				.filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
				.forEach(entry -> sendToClient(emitter, entry.getKey(), entry.getValue()));
		}

		return emitter;
	}

	private void sendToClient(SseEmitter emitter, String id, Object data) {
		try {
			emitter.send(SseEmitter.event()
				.id(id)
				.name("sse")
				.data(data));
		} catch (IOException exception) {
			emitterRepository.deleteAllStartByWithId(id);
			log.error("SSE 연결 오류났어여!!!!!!", exception);
		}
	}

	@Transactional
	public void send(User user, Long idForNotifyType, String title, String content, String commenterImage,
		NotificationType notificationType) {
		Notification notification = createNotification(user, idForNotifyType, title, content, commenterImage,
			notificationType);
		String id = String.valueOf(user.getId());
		notificationRepository.save(notification);
		Map<String, SseEmitter> sseEmitters = emitterRepository.findAllStartById(id);
		sseEmitters.forEach(
			(key, emitter) -> {
				emitterRepository.saveEventCache(key, notification);
				sendToClient(emitter, key, content == null ?
					NotificationDto.FollowResponse.from(notification) :
					NotificationDto.Response.from(notification));
			}
		);
	}

	private Notification createNotification(User user, Long idForNotifyType, String title, String content,
		String commenterImage,
		NotificationType notificationType) {
		Notification notification = Notification.builder()
			.user(user)
			.title(title)
			.idForNotifyType(idForNotifyType)
			.isRead(false)
			.content(content)
			.commenterImage(commenterImage)
			.notificationType(notificationType)
			.build();

		return notificationRepository.save(notification);
	}

	@Transactional
	public NotificationDto.Total findAll() {
		User user = userService.getLoginUser();

		List<NotificationDto.Response> responses = notificationRepository.findAllByUserId(user.getId()).stream()
			.map(NotificationDto.Response::from)
			.sorted(Comparator.comparing(NotificationDto.Response::getId).reversed())
			.collect(Collectors.toList());

		long unreadCount = responses.stream()
			.filter(notification -> !notification.getIsRead())
			.count();

		return NotificationDto.Total.of(responses, unreadCount);
	}

	@Transactional
	public void readNotification(Long id) {
		Notification notification = notificationRepository.findById(id)
			.orElseThrow(() -> new BusinessLogicException(ExceptionCode.NOTIFICATION_NOT_FOUND));
		notification.read();
		em.flush();
	}

	@Transactional
	public void deleteNotification(Long id) {
		Notification notification = notificationRepository.findById(id).orElseThrow();
		notificationRepository.delete(notification);
	}

	@Transactional
	public void deleteAll() {
		User user = userService.getLoginUser();
		notificationQRepository.deleteAllNotification(user.getId());
	}
}
