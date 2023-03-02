package be.global.chat.redis.entity;

import java.io.Serializable;
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

import org.hibernate.annotations.CreationTimestamp;

import be.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor//(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RedisChatRoom implements Serializable {
	private static final long serialVersionUID = 6494678977089006639L;

	@Id
	@Column(name = "redis_room_id")
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_room_sender"))
	private User sender;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_room_receiver"))
	private User receiver;

	@CreationTimestamp
	@Column(name = "created_at")
	private LocalDateTime createdAt;

	public RedisChatRoom(User sender, User receiver) {
		this.sender = sender;
		this.receiver = receiver;
	}

	public static RedisChatRoom create(User user) {
		RedisChatRoom chatRoom = new RedisChatRoom();
		chatRoom.sender = user;
		chatRoom.createdAt = LocalDateTime.now();

		return chatRoom;
	}
}
