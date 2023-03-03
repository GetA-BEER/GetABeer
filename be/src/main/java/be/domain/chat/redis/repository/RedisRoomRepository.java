package be.domain.chat.redis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import be.domain.chat.redis.entity.RedisChatRoom;

@Repository
public interface RedisRoomRepository extends JpaRepository<RedisChatRoom, Long> {
}
