package be.domain.chat.kafka.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

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
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String roomName;

	/* 회원이랑 일대알 매핑 -> 어드민은 Get으로 등록? */
	// @OneToOne
	// @JoinColumn(name = "user_id")
	// private User user;
	//
	// public void bndUser(User user) {
	// 	this.user = user;
	//
	// 	if (user.getChatRoom() != this) {
	// 		user.bndChatRoom(this);
	// 	}
	// }

	public static KafkaChatRoom create(User user) {
		KafkaChatRoom room = new KafkaChatRoom();
		room.id = user.getId();
		room.roomName = user.getNickname() + user.getId() + "님의 채팅방입니다.";
		return room;
	}
}
