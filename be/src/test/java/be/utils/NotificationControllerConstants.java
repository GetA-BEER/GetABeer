package be.utils;

import java.time.LocalDateTime;
import java.util.List;

import be.domain.notice.dto.NotificationDto;
import be.domain.notice.entity.NotificationType;

public class NotificationControllerConstants {

	public static final NotificationDto.Response NOTIFICATION_RESPONSE_DTO =
		NotificationDto.Response.builder()
			.id(1L)
			.title("알림 내용")
			.content("대상 컨텐츠")
			.idForNotifyType(1L)
			.createdAt(LocalDateTime.now())
			.isRead(false)
			.notifyType(NotificationType.RATING)
			.commentUserImage("프로필 사진")
			.build();

	public static final List<NotificationDto.Response> NOTIFICATION_RESPONSE_LIST =
		List.of(NOTIFICATION_RESPONSE_DTO, NOTIFICATION_RESPONSE_DTO);

	public static final NotificationDto.Total NOTIFICATION_TOTAL_DTO =
		NotificationDto.Total.builder()
			.notifications(NOTIFICATION_RESPONSE_LIST)
			.unreadCount(1L)
			.build();
}
