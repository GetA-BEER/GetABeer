package be.global.chat;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor /* 메세지 : 신고용, 건의용 */
public class Message implements Serializable {
	private Long roomId; /* 채팅방 아이디 */
	private String sender;
	private String content;
	private String timestamp;
}