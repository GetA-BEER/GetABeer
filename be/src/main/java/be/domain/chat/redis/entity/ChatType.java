package be.domain.chat.redis.entity;

public enum ChatType {
	SUGGEST("SUGGEST"), REPORT("REPORT");

	private final String type;

	ChatType(String type) {
		this.type = type;
	}

	public static ChatType to(String type) {
		for (ChatType chatType : ChatType.values()) {
			if (chatType.getType().equalsIgnoreCase(type)){
				return chatType;
			}
		}

		throw new RuntimeException("타입을 확인하세요.");
	}

	public String getType() {
		return type;
	}
}
