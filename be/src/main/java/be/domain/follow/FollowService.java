package be.domain.follow;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import be.domain.user.entity.User;
import be.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class FollowService {

	private final FollowRepository followRepository;
	private final FollowQueryRepository followQueryRepository;
	private final UserService userService;

	public String createOrDeleteFollow(Long followedUserId) {

		User followingUser = userService.getLoginUser();
		Long followingUserId = followingUser.getId();

		User followedUser = userService.findVerifiedUser(followedUserId);

		if (followQueryRepository.findFollowByUserIds(followingUserId, followedUserId) == null) {

			Follow createdFollow = Follow.builder()
				.followingUser(followingUser)
				.followedUser(followedUser)
				.build();

			followRepository.save(createdFollow);

			followingUser.addFollowing();
			followedUser.addFollower();

			return "Create Follow";

		} else {

			Follow findFollow = followQueryRepository.findFollowByUserIds(followingUserId, followedUserId);

			followRepository.delete(findFollow);

			followingUser.removeFollowing();
			followedUser.removeFollower();

			return "Delete Follow";
		}
	}

	public Page<User> findFollowers(Long userId, Integer page) {

		PageRequest pageRequest = PageRequest.of(page - 1, 10);

		return followQueryRepository.findFollowersByUserId(userId, pageRequest);
	}

	public List<User> findFollowingsList(Long followedUserId, Page<User> userPage) {
		return userPage.stream()
			.filter(user -> followQueryRepository.findFollowByUserIds(user.getId(), followedUserId) != null)
			.collect(Collectors.toList());
	}

	public Page<User> findFollowings(Long userId, Integer page) {

		PageRequest pageRequest = PageRequest.of(page - 1, 10);

		return followQueryRepository.findFollowingsByUserId(userId, pageRequest);
	}
}
