package be.global.chat.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import be.domain.user.entity.User;
import be.domain.user.service.UserService;
import be.global.chat.ChatRoom;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ChatRoomRepository {
	private Map<Long, ChatRoom> chatRooms;
	private final UserService userService;

	@PostConstruct
	public void init() {
		chatRooms = new LinkedHashMap<>();
	}

	/* 채팅방 전체 조회 : Only Admin */
	public List<ChatRoom> findAllChatRoom() {
		List<ChatRoom> list = new ArrayList<>(chatRooms.values());
		Collections.reverse(list);

		return list;
	}

	/* 채팅방 번호로 조회 */
	public ChatRoom findById(Long chatRoomId) {

		return chatRooms.get(chatRoomId);
	}

	/* 채팅방 생성 */
	public ChatRoom createChatRoom() {
		User user = userService.findLoginUser();
		ChatRoom chatRoom = ChatRoom.create(user);
		chatRooms.put(chatRoom.getId(), chatRoom);

		return chatRoom;
	}
}
