package be.domain.follow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import be.domain.follow.entity.Follow;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {

	@Query(nativeQuery = true, value = "select following_user_id from follow where followed_User_Id =:userId")
	List<Long> findFollowersByUserId(Long userId);

	@Query(nativeQuery = true, value = "select followed_user_id from follow where following_User_Id =:userId")
	List<Long> findFollowingsByUserId(Long userId);
}
