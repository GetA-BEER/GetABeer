package be.domain.user.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import be.domain.comment.entity.PairingComment;
import be.domain.comment.entity.RatingComment;
import be.domain.comment.repository.pairing.PairingCommentRepository;
import be.domain.comment.repository.rating.RatingCommentRepository;
import be.domain.follow.repository.FollowQueryRepository;
import be.domain.pairing.entity.Pairing;
import be.domain.pairing.repository.PairingRepository;
import be.domain.rating.entity.Rating;
import be.domain.rating.repository.RatingRepository;
import be.domain.user.dto.UserDto;
import be.domain.user.entity.User;
import be.domain.user.repository.UserQueryRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserPageService {

	private final UserService userService;
	private final RatingRepository ratingRepository;
	private final PairingRepository pairingRepository;
	private final RatingCommentRepository ratingCommentRepository;
	private final PairingCommentRepository pairingCommentRepository;
	private final UserQueryRepository userQueryRepository;
	private final FollowQueryRepository followQueryRepository;

	/**
	 * 마이페이지
	 */
	/* 나의 평가 */
	public Page<Rating> getUserRating(int page) {
		User user = userService.getLoginUser();
		PageRequest pageRequest = PageRequest.of(page - 1, 10);

		return ratingRepository.findRatingByUser(user, pageRequest);
	}

	/* 나의 레이팅 코멘트 */
	public Page<RatingComment> getUserRatingComment(int page) {
		User user = userService.getLoginUser();
		PageRequest pageRequest = PageRequest.of(page - 1, 10);

		return ratingCommentRepository.findRatingCommentByUser(user, pageRequest);
	}

	/* 나의 페어링 코멘트 */
	public Page<PairingComment> getUserPairingComment(int page) {
		User user = userService.getLoginUser();
		PageRequest pageRequest = PageRequest.of(page - 1, 10);

		return pairingCommentRepository.findPairingCommentByUser(user, pageRequest);
	}

	/* 나의 페어링 */
	public Page<Pairing> getUserPairing(int page) {
		User user = userService.getLoginUser();
		PageRequest pageRequest = PageRequest.of(page - 1, 10);

		return pairingRepository.findPairingByUser(user, pageRequest);
	}

	// ----------------------------------------다른 유저 페이지-----------------------------------------
	@Transactional(readOnly = true)
	public UserDto.UserPageResponse getUserPage(Long userId) {

		UserDto.UserPageResponse userPageResponse = userQueryRepository.findUserPage(userId);

		if (userService.getLoginUserReturnNull() == null) {
			userPageResponse.addIsFollowing(null);
		} else {
			Long loginUserId = userService.getLoginUser().getId();
			userPageResponse.addIsFollowing(followQueryRepository.findFollowByUserIds(loginUserId, userId) != null);
		}

		return userPageResponse;
	}

	@Transactional(readOnly = true)
	public Page<Rating> getUserRatingByUserId(Long userId, Integer page) {

		User findUser = userService.findVerifiedUser(userId);

		PageRequest pageRequest = PageRequest.of(page - 1, 10);

		return ratingRepository.findRatingByUser(findUser, pageRequest);
	}

	@Transactional(readOnly = true)
	public Page<Pairing> getUserPairingByUserId(Long userId, Integer page) {

		User findUser = userService.findVerifiedUser(userId);

		PageRequest pageRequest = PageRequest.of(page - 1, 10);

		return pairingRepository.findPairingByUser(findUser, pageRequest);
	}
}
