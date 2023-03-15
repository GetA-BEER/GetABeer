package be.domain.notice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import be.domain.notice.dto.NotificationDto;
import be.domain.notice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Validated
@RestController
@RequestMapping
@RequiredArgsConstructor
public class NotificationController {

	private final NotificationService notificationService;

	/**
	 * SSE 통신
	 * 로그인 유저 sse 연결
	 * @param lastEventId
	 * @return
	 */
	@GetMapping(value = "/subscribe", produces = "text/event-stream")
	public SseEmitter testNotice(@RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId) {
		return notificationService.subscribe(lastEventId);
	}

	/**
	 * 로그인 유저 알림 전체 조회
	 */
	@GetMapping("/api/notifications")
	public ResponseEntity<NotificationDto.Total> getNotifications() {
		return ResponseEntity.ok().body(notificationService.findAll());
	}

	/**
	 * 알림 클릭
	 */
	@PatchMapping("/api/notifications/{notification-id}")
	public void readNotification(@PathVariable(name = "notification-id") Long id) {
		notificationService.readNotification(id); // 알림 읽음처리
	}

	/**
	 * 알림 삭제
	 */
	@DeleteMapping("/api/notifications/{notification-id}")
	public void deleteNotification(@PathVariable(name = "notification-id") Long id) {
		notificationService.deleteNotification(id);
	}

	/**
	 * 전체 알림 삭제
	 */
	@DeleteMapping("/api/notifications")
	public void deleteAll() {
		notificationService.deleteAll();
	}
}
