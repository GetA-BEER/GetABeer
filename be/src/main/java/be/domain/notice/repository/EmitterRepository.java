package be.domain.notice.repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Repository
public class EmitterRepository {

	public final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();
	private final Map<String, Object> eventCache = new ConcurrentHashMap<>();

	public SseEmitter save(String id, SseEmitter sseEmitter) {
		emitters.put(id, sseEmitter);
		return sseEmitter;
	}

	public void saveEventCache(String id, Object event) {
		eventCache.put(id, event);
	}

	public Map<String, SseEmitter> findAllStartById(String id) {
		return emitters.entrySet().stream()
			.filter(entry -> entry.getKey().startsWith(id))
			.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}

	public void deleteAllStartByWithId(String id) {
		emitters.forEach((key, emitter) -> {
			if (key.startsWith(id)) {
				emitters.remove(key);
			}
		});
	}
}
