package be.domain.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import be.domain.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);

	Boolean existsByNickname(String nickname);

	Boolean findByNickname(String nickname);

	Optional<User> findByEmailAndProvider(String email, String provider);

	User findByProviderId(String providerId);

	@Query(value = "SELECT u.nickname FROM User u")
	List<Object[]> indexTest();
}

