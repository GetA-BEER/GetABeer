package be.domain.chatting.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import be.global.BaseTimeEntity;

@Entity
public class ChatMessage extends BaseTimeEntity {

	@Id
	@Column(name = "chat_message_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String content;
}
