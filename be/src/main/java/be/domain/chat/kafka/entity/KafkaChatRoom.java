package be.domain.chat.kafka.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import be.domain.user.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class KafkaChatRoom implements Serializable {

	private static final long serialVersionUID = 6494678977089006639L;

	@Id
	@Column(name = "room_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String roomName;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "room_user_id"))
	private User roomUser;

	public static KafkaChatRoom create(User user) {

		if (user.getRoles().contains("USER_ADMIN")) {
			throw new RuntimeException("관리자는 채팅방을 개설할 수 없습니다.");
		}

		KafkaChatRoom room = new KafkaChatRoom();
		room.roomName = user.getNickname() + user.getId() + "님의 채팅방입니다.";
		room.roomUser = user;
		return room;
	}
}
