package be.domain.rating.controller;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import be.domain.rating.dto.RatingRequestDto;
import be.domain.rating.dto.RatingResponseDto;
import be.domain.rating.entity.RatingTag;
import be.domain.rating.mapper.RatingMapper;
import be.domain.rating.mapper.RatingTagMapper;
import be.domain.rating.service.RatingService;
import be.global.dto.MultiResponseDto;

@Validated
@RestController
@RequestMapping("/api/ratings")
public class RatingController {
	private final RatingService ratingService;
	private final RatingMapper ratingMapper;
	private final RatingTagMapper tagMapper;

	public RatingController(RatingService ratingService, RatingMapper ratingMapper, RatingTagMapper tagMapper) {
		this.ratingService = ratingService;
		this.ratingMapper = ratingMapper;
		this.tagMapper = tagMapper;
	}

	/* 맥주 평가 등록 -> 성공 실패 여부만 리턴 */
	@PostMapping
	public ResponseEntity<String> post(@RequestBody @Valid RatingRequestDto.Post post) {
		ratingService.checkVerifiedTag(post.getColor(), post.getTaste(), post.getFlavor(), post.getCarbonation());

		RatingTag ratingTag = tagMapper.ratingPostDtoToRatingTag(post);
		String message = ratingService
			.create(ratingMapper.ratingPostDtoToRating(post), post.getBeerId(), ratingTag, post.getUserId());

		return ResponseEntity.status(HttpStatus.CREATED).body(message);
	}

	/* 맥주 평가 수정 -> 성공 실패 여부만 리턴 */
	@PatchMapping("/{ratingId}")
	public ResponseEntity<String> patch(@PathVariable @Positive Long ratingId,
		@RequestBody @Valid RatingRequestDto.Patch patch) {

		RatingTag ratingTag = tagMapper.ratingPatchDtoToRatingTag(patch);

		String message = ratingService.update(ratingMapper.ratingPatchDtoToRating(patch),
			ratingId, ratingTag);

		return ResponseEntity.ok(message);
	}

	/* 맥주 코멘트 삭제 */
	@DeleteMapping("/{ratingId}")
	public ResponseEntity<String> delete(@PathVariable @Positive Long ratingId) {

		return ResponseEntity.ok(ratingService.delete(ratingId));
	}

	/* 특정 맥주 평가 상세 조회 */
	@GetMapping("/{ratingId}")
	public ResponseEntity<RatingResponseDto.Detail> getRating(@PathVariable @Positive Long ratingId) {

		return ResponseEntity.ok(ratingService.getRatingResponse(ratingId));
	}

	/* 맥주 평가 페이지 조회 */
	@GetMapping("/page/{type}")
	public ResponseEntity<MultiResponseDto<RatingResponseDto.Total>> getRatingPageOrderByRecently(
		@PathVariable String type, @RequestParam Long beerId, @RequestParam Integer page, @RequestParam Integer size) {
		Page<RatingResponseDto.Total> responses = ratingService.getRatingPageOrderBy(beerId, page, size, type);

		return ResponseEntity.ok(new MultiResponseDto<>(responses.getContent(), responses));
	}
}
