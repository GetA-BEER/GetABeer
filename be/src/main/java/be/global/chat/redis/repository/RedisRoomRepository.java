package be.global.chat.redis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import be.global.chat.redis.entity.RedisChatRoom;

@Repository
public interface RedisRoomRepository extends JpaRepository<RedisChatRoom, Long> {
}
