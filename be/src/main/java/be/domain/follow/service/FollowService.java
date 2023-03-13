package be.domain.follow.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import be.domain.follow.entity.Follow;
import be.domain.follow.repository.FollowQueryRepository;
import be.domain.follow.repository.FollowRepository;
import be.domain.notice.entity.NotificationType;
import be.domain.notice.service.NotificationService;
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
	private final NotificationService notificationService;

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

			String title = followingUser.getNickname() + "님이 회원님을 팔로우하기 시작했습니다.";
			notificationService.send(followedUser, followingUser.getId(), title, null, followingUser.getImageUrl(),
				NotificationType.FOLLOWING);

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

	public Page<User> findFollowersWithLoginUser(Long loginUserId, Long userId, Integer page) {

		PageRequest pageRequest = PageRequest.of(page - 1, 10);

		return followQueryRepository.findFollowersWithLoginUserByUserId(loginUserId, userId, pageRequest);
	}

	public List<User> findFollowingsList(Long followingUserId, Page<User> userPage) {
		return userPage.stream()
			.filter(user -> followQueryRepository.findFollowByUserIds(followingUserId, user.getId()) != null)
			.collect(Collectors.toList());
	}

	public Page<User> findFollowings(Long userId, Integer page) {

		PageRequest pageRequest = PageRequest.of(page - 1, 10);

		return followQueryRepository.findFollowingsByUserId(userId, pageRequest);
	}

	public Boolean isFollowing(Long followingUserId, Long followedUserId) {
		return followQueryRepository.findFollowByUserIds(followingUserId, followedUserId) != null;
	}
}
