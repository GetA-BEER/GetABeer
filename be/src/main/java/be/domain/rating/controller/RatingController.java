package be.domain.rating.controller;

import javax.validation.constraints.Positive;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import be.domain.rating.dto.RatingDto;
import be.domain.rating.entity.Rating;
import be.domain.rating.mapper.RatingMapper;
import be.domain.rating.service.RatingService;

@RestController
@RequestMapping("/rating")
public class RatingController {
	private final RatingService ratingService;
	private final RatingMapper mapper;

	public RatingController(RatingService ratingService, RatingMapper mapper) {
		this.ratingService = ratingService;
		this.mapper = mapper;
	}

	/* 맥주 코멘트 등록 */
	@PostMapping
	public ResponseEntity<RatingDto.Response> post(@RequestBody RatingDto.Post post) {
		Rating beerComment = ratingService.create(mapper.beerCommentPostDtoToBeerComment(post));

		return ResponseEntity.status(HttpStatus.CREATED).body(mapper.beerCommentToBeerCommentResponse(beerComment));
	}

	/* 맥주 코멘트 수정 */
	@PatchMapping("/{beerCommentId}")
	public ResponseEntity patch(@PathVariable @Positive Long beerCommentId, @RequestBody RatingDto.Patch patch) {
		Rating beerComment = ratingService.update(mapper.beerCommentPatchDtoToBeerComment(patch),
			beerCommentId);

		return ResponseEntity.ok(mapper.beerCommentToBeerCommentResponse(beerComment));
	}

	/* 특정 맥주 코멘트 상세 조회 */
	@GetMapping("/{beerCommentId}")
	public ResponseEntity getComment(@PathVariable @Positive Long beerCommentId) {

		return ResponseEntity.ok(mapper.beerCommentToBeerCommentResponse(ratingService.getComment(beerCommentId)));
	}

	/* 맥주 코멘트 페이지 조회 */
	public ResponseEntity getCommentPage() {
		return null;
	}

	/* 맥주 코멘트 삭제 */
	@DeleteMapping("/{beerCommentId}")
	public ResponseEntity<String> delete(@PathVariable @Positive Long beerCommentId) {

		return ResponseEntity.ok(ratingService.delete(beerCommentId));
	}
}
