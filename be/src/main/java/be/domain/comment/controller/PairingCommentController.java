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

import be.domain.comment.dto.PairingCommentDto;
import be.domain.comment.entity.PairingComment;
import be.domain.comment.mapper.PairingCommentMapper;
import be.domain.comment.service.PairingCommentService;

@RestController
@RequestMapping("/api/pairings/comments")
public class PairingCommentController {
	private final PairingCommentService pairingCommentService;
	private final PairingCommentMapper mapper;

	public PairingCommentController(PairingCommentService pairingCommentService, PairingCommentMapper mapper) {
		this.pairingCommentService = pairingCommentService;
		this.mapper = mapper;
	}

	/* 페어링 댓글 등록 */
	@PostMapping
	public ResponseEntity<PairingCommentDto.Response> post(@RequestBody PairingCommentDto.Post post) {
		PairingComment pairingComment = pairingCommentService
			.create(mapper.postPairingCommentDtoToPairingComment(post), post.getPairingId(), post.getUserId());

		return ResponseEntity.status(HttpStatus.CREATED).body(mapper.pairingCommentToPairingResponse(pairingComment));
	}

	/* 페어링 댓글 수정 */
	@PatchMapping("/{commentId}")
	public ResponseEntity<PairingCommentDto.Response>  patch(@RequestBody PairingCommentDto.Patch patch,
		@PathVariable @Positive Long commentId) {
		PairingComment pairingComment = pairingCommentService
			.update(mapper.patchPairingCommentDtoToPairingComment(patch), commentId);

		return ResponseEntity.ok(mapper.pairingCommentToPairingResponse(pairingComment));
	}

	/* 페어링 댓글 조회 */
	@GetMapping
	public ResponseEntity<List<PairingCommentDto.Response>>  getPairingCommentPage(@RequestParam Long pairingId) {
		List<PairingCommentDto.Response> responses = pairingCommentService.getPairingComment(pairingId);

		return ResponseEntity.ok(responses);
	}

	/* 페어링 댓글 삭제 */
	@DeleteMapping("/{commentId}")
	public ResponseEntity<String> delete(@PathVariable @Positive Long commentId) {
		String message = pairingCommentService.delete(commentId);

		return ResponseEntity.ok(message);
	}
}
