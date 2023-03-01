package be.domain.notice.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import be.domain.pairing.entity.Pairing;
import be.domain.rating.entity.Rating;
import be.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String title; // 알림 제목(?) "~~님이 ~~에 코멘트를 등록하셨습니다" 와 같은..?

	@Column
	private String content;

	@Column
	private Long idForNotifyType; // 알림 클릭 시 해당 위치로 이동되게

	@ColumnDefault("false")
	private Boolean isRead; // 읽었는지 안읽었는지

	@Column
	private String commenterImage;

	@Enumerated(EnumType.STRING)
	private NotificationType notificationType;

	@CreatedDate
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt; // 몇분전.. 등등 위함

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	public void setUser(User user) {
		this.user = user;
	}

	public void read() {
		this.isRead = true;
	}

	@Builder
	public Notification(User user, String title, Long idForNotifyType, Boolean isRead, String content, String commenterImage, NotificationType notificationType) {
		this.user = user;
		this.title = title;
		this.idForNotifyType = idForNotifyType;
		this.isRead = isRead;
		this.commenterImage = commenterImage;
		this.createdAt = LocalDateTime.now();
		this.notificationType = notificationType;
		this.content = content;
	}
}
