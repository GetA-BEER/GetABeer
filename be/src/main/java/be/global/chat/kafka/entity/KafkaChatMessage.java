package be.global.chat.kafka.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor /* 메세지 : 신고용, 건의용 */
public class KafkaChatMessage {

	@Id
	private Long id; /* 메세지 아이디 */
	private Long roomId;
	private String sender;
	private String content;
	private String timestamp;
}