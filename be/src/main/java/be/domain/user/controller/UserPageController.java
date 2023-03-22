package be.domain.user.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import be.domain.beer.dto.BeerDto;
import be.domain.comment.dto.PairingCommentDto;
import be.domain.comment.dto.RatingCommentDto;
import be.domain.comment.entity.PairingComment;
import be.domain.comment.entity.RatingComment;
import be.domain.comment.mapper.PairingCommentMapper;
import be.domain.comment.mapper.RatingCommentMapper;
import be.domain.like.repository.PairingLikeRepository;
import be.domain.like.repository.RatingLikeRepository;
import be.domain.pairing.dto.PairingResponseDto;
import be.domain.pairing.entity.Pairing;
import be.domain.pairing.mapper.PairingMapper;
import be.domain.rating.dto.RatingResponseDto;
import be.domain.rating.entity.Rating;
import be.domain.rating.mapper.RatingMapper;
import be.domain.user.dto.MyPageMultiResponseDto;
import be.domain.user.dto.UserDto;
import be.domain.user.service.UserPageService;
import be.domain.user.service.UserService;
import be.global.dto.MultiResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Validated
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserPageController {

	private final UserService userService;
	private final RatingMapper ratingMapper;
	private final PairingMapper pairingMapper;
	private final UserPageService userPageService;
	private final RatingCommentMapper ratingCommentMapper;
	private final PairingCommentMapper pairingCommentMapper;
	private final RatingLikeRepository ratingLikeRepository;
	private final PairingLikeRepository pairingLikeRepository;

	/**
	 * 마이페이지
	 */

	/* 나의 평가 */
	@GetMapping("/mypage/ratings")
	public ResponseEntity<MyPageMultiResponseDto<RatingResponseDto.MyPageResponse>> getMyRatings(
		@RequestParam(name = "page", defaultValue = "1") Integer page) {
		Page<Rating> ratings = userPageService.getUserRating(page);
		Page<RatingResponseDto.MyPageResponse> response = ratingMapper.ratingToRatingResponse(
			ratings, ratingLikeRepository);

		return ResponseEntity.ok(
			new MyPageMultiResponseDto<>(userService.getLoginUser().getNickname(), response.getContent(), response));
	}

	/* 나의 페어링 코멘트 */
	@GetMapping("/mypage/comment/pairing")
	public ResponseEntity<MyPageMultiResponseDto<PairingCommentDto.Response>> getMyPairingComments(
		@RequestParam(name = "page", defaultValue = "1") Integer page) {
		Page<PairingComment> pairingComments = userPageService.getUserPairingComment(page);
		Page<PairingCommentDto.Response> responses = pairingCommentMapper.pairingCommentsToPageResponse(
			pairingComments);

		return ResponseEntity.ok(
			new MyPageMultiResponseDto<>(userService.getLoginUser().getNickname(), responses.getContent(), responses));
	}

	/* 나의 레이팅 코멘트 */
	@GetMapping("/mypage/comment/rating")
	public ResponseEntity<MyPageMultiResponseDto<RatingCommentDto.Response>> getMyRatingComments(
		@RequestParam(name = "page", defaultValue = "1") Integer page) {
		Page<RatingComment> ratingComments = userPageService.getUserRatingComment(page);
		Page<RatingCommentDto.Response> responses = ratingCommentMapper.ratingCommentsToResponsePage(
			ratingComments);

		return ResponseEntity.ok(
			new MyPageMultiResponseDto<>(userService.getLoginUser().getNickname(), responses.getContent(), responses));
	}

	/* 나의 페어링 */
	@GetMapping("/mypage/pairing")
	public ResponseEntity<MyPageMultiResponseDto<PairingResponseDto.Total>> getMyPairing(
		@RequestParam(name = "page", defaultValue = "1") Integer page) {
		Page<Pairing> pairings = userPageService.getUserPairing(page);
		Page<PairingResponseDto.Total> response = pairingMapper.pairingToPairingResponse(pairings,
			pairingLikeRepository);

		return ResponseEntity.ok(
			new MyPageMultiResponseDto<>(userService.getLoginUser().getNickname(), response.getContent(), response));
	}

	// ----------------------------------------다른 유저 페이지-----------------------------------------
	@GetMapping("/user/{user_Id}")
	public ResponseEntity<UserDto.UserPageResponse> readUserPage(@PathVariable("user_Id") Long userId) {

		UserDto.UserPageResponse userPageResponse = userPageService.getUserPage(userId);
		return ResponseEntity.ok().body(userPageResponse);
	}

	@GetMapping("/user/{user_Id}/ratings")
	public ResponseEntity<MultiResponseDto<RatingResponseDto.UserPageResponse>> getUserRatings(
		@PathVariable("user_Id") Long userId,
		@RequestParam(name = "page", defaultValue = "1") Integer page) {

		Page<Rating> ratingPage = userPageService.getUserRatingByUserId(userId, page);
		PageImpl<RatingResponseDto.UserPageResponse> responsePage =
			ratingMapper.ratingToUserRatingResponse(ratingPage, ratingLikeRepository);

		return ResponseEntity.ok(new MultiResponseDto<>(responsePage.getContent(), ratingPage));
	}

	@GetMapping("/user/{user_Id}/pairings")
	public ResponseEntity<MultiResponseDto<PairingResponseDto.UserPageResponse>> getUserPairings(
		@PathVariable("user_Id") Long userId,
		@RequestParam(name = "page", defaultValue = "1") Integer page) {

		Page<Pairing> pairingPage = userPageService.getUserPairingByUserId(userId, page);
		Page<PairingResponseDto.UserPageResponse> responsePage =
			pairingMapper.pairingToUserPairingResponse(pairingPage, pairingLikeRepository);

		return ResponseEntity.ok(new MultiResponseDto<>(responsePage.getContent(), pairingPage));
	}
}
