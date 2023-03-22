package be.domain.notice.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.poi.ss.formula.functions.T;

import be.domain.notice.entity.Notification;
import be.domain.notice.entity.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class NotificationDto {

	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Response {
		private Long id;
		private String commentUserImage;
		private String title;
		private String content;
		private NotificationType notifyType;
		private Long idForNotifyType;
		private LocalDateTime createdAt;
		private Boolean isRead;

		public static NotificationDto.Response from(Notification notification) {
			return Response.builder()
				.id(notification.getId())
				.title(notification.getTitle())
				.content(notification.getContent())
				.idForNotifyType(notification.getIdForNotifyType())
				.createdAt(notification.getCreatedAt())
				.isRead(notification.getIsRead())
				.notifyType(notification.getNotificationType())
				.commentUserImage(notification.getCommenterImage())
				.build();
		}
	}

	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class FollowResponse {
		private Long id;
		private String commentUserImage;
		private String title;
		private NotificationType notifyType;
		private LocalDateTime createdAt;
		private Boolean isRead;

		public static NotificationDto.FollowResponse from(Notification notification) {
			return FollowResponse.builder()
				.id(notification.getId())
				.title(notification.getTitle())
				.createdAt(notification.getCreatedAt())
				.isRead(notification.getIsRead())
				.notifyType(notification.getNotificationType())
				.commentUserImage(notification.getCommenterImage())
				.build();
		}
	}

	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Total {
		private List<Response> notifications;
		private Long unreadCount;

		public static NotificationDto.Total of(List<NotificationDto.Response> notificationResponses, long count) {
			return Total.builder()
				.notifications(notificationResponses)
				.unreadCount(count)
				.build();
		}
	}
}
