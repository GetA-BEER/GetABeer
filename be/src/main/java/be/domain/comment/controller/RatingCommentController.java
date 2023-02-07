package be.domain.comment.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import be.domain.comment.dto.RatingCommentDto;
import be.domain.comment.entity.RatingComment;
import be.domain.comment.mapper.RatingCommentMapper;
import be.domain.comment.service.RatingCommentService;

@RestController
@RequestMapping("/api/ratings/comments")
public class RatingCommentController {
	private final RatingCommentService ratingCommentService;
	private final RatingCommentMapper mapper;

	public RatingCommentController(RatingCommentService ratingCommentService, RatingCommentMapper mapper) {
		this.ratingCommentService = ratingCommentService;
		this.mapper = mapper;
	}

	/* 맥주 댓글 등록 */
	@PostMapping
	public ResponseEntity<RatingCommentDto.Response> post(@RequestBody RatingCommentDto.Post post) {
		RatingComment ratingComment = ratingCommentService
			.create(mapper.ratingCommentPostDtoToRatingComment(post), post.getRatingId(), post.getUserId());

		return ResponseEntity.status(HttpStatus.CREATED)
			.body(mapper.ratingCommentToRatingCommentResponse(ratingComment));
	}

	/* 맥주 댓글 수정 */
	@PatchMapping("/{commentId}")
	public ResponseEntity patch(@PathVariable @Positive Long commentId, @RequestBody RatingCommentDto.Patch patch) {
		RatingComment ratingComment =
			ratingCommentService.update(mapper.ratingCommentPatchDtoToRatingComment(patch), commentId);

		return ResponseEntity.ok(mapper.ratingCommentToRatingCommentResponse(ratingComment));
	}

	/* 맥주 댓글 조회 : 최신순 + 무한 스크롤 */
	@GetMapping
	public ResponseEntity getPairingCommentPage(@RequestParam @Positive Long ratingId) {
		List<RatingCommentDto.Response> list = ratingCommentService.getRatingComment(ratingId);

		return ResponseEntity.ok(list);
	}

	/* 맥주 댓글 삭제 */
	@DeleteMapping("/{commentId}")
	public ResponseEntity<String> delete(@PathVariable @Positive Long commentId) {
		String message = ratingCommentService.delete(commentId);

		return ResponseEntity.ok(message);
	}
}
