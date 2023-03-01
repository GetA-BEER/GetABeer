package be.global.chat;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor /* 메세지 : 신고용, 건의용 */
public class Message implements Serializable {

	// private Long id; /* 메세지 아이디 */
	private Long roomId;
	private String sender;
	private String content;
	private String timestamp;

	// @ManyToOne
	// @JoinColumn(name = "room_id")
	// private ChatRoom chatRoom;
	//
	// public void belongToChatRoom(ChatRoom chatRoom) {
	// 	this.chatRoom = chatRoom;
	//
	// 	if (!chatRoom.getMessageList().contains(this)) {
	// 		chatRoom.addMessageList(this);
	// 	}
	// }
}