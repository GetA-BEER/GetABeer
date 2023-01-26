package be.domain.chatting.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ChatRoom {

	@Id
	@Column(name = "chat_room_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/* 회원이랑 일대일 매핑 */
}
