package be.global.chat.redis.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import be.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RedisChatMessage {

	@Id
	@Column(name = "redis_message_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_chat_sender"))
	private User sender;

	/* 관리자 -> 클라이언트에게는 가능한 데, 클라이언트 -> 관리자? */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_chat_receiver"))
	private User receiver;

	@Column(nullable = false)
	private String content;

	@Column
	private String type;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, name = "room")
	private RedisChatRoom chatRoom;

	@Column(name = "created_at")
	private LocalDateTime createdAt;
}
