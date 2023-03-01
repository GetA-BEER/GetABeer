package be.global.chat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import be.domain.user.entity.User;
import lombok.Getter;

/* 채팅방은 WEB Socket? */
@Getter
@Entity
public class ChatRoom {

	@Id
	@Column(name = "room_id")
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

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

	public static ChatRoom create(User user) {
		ChatRoom room = new ChatRoom();
		room.id = user.getId();
		return room;
	}
}
