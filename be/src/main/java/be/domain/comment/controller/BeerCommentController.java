package be.domain.comment.controller;

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

import be.domain.comment.dto.BeerCommentDto;
import be.domain.comment.entity.BeerComment;
import be.domain.comment.mapper.BeerCommentMapper;
import be.domain.comment.service.BeerCommentService;

@RestController
@RequestMapping("/comments")
public class BeerCommentController {
	private final BeerCommentService beerCommentService;
	private final BeerCommentMapper mapper;

	public BeerCommentController(BeerCommentService beerCommentService, BeerCommentMapper mapper) {
		this.beerCommentService = beerCommentService;
		this.mapper = mapper;
	}

	/* 맥주 코멘트 등록 */
	@PostMapping
	public ResponseEntity<BeerCommentDto.Response> post(@RequestBody BeerCommentDto.Post post) {
		BeerComment beerComment = beerCommentService.create(mapper.beerCommentPostDtoToBeerComment(post));

		return ResponseEntity.status(HttpStatus.CREATED).body(mapper.beerCommentToBeerCommentResponse(beerComment));
	}

	/* 맥주 코멘트 수정 */
	@PatchMapping("/{beerCommentId}")
	public ResponseEntity patch(@PathVariable @Positive Long beerCommentId, @RequestBody BeerCommentDto.Patch patch) {
		BeerComment beerComment = beerCommentService.update(mapper.beerCommentPatchDtoToBeerComment(patch),
			beerCommentId);

		return ResponseEntity.ok(mapper.beerCommentToBeerCommentResponse(beerComment));
	}

	/* 특정 맥주 코멘트 상세 조회 */
	@GetMapping("/{beerCommentId}")
	public ResponseEntity getComment(@PathVariable @Positive Long beerCommentId) {

		return ResponseEntity.ok(mapper.beerCommentToBeerCommentResponse(beerCommentService.getComment(beerCommentId)));
	}

	/* 맥주 코멘트 페이지 조회 */
	public ResponseEntity getCommentPage() {
		return null;
	}

	/* 맥주 코멘트 삭제 */
	@DeleteMapping("/{beerCommentId}")
	public ResponseEntity<String> delete(@PathVariable @Positive Long beerCommentId) {

		return ResponseEntity.ok(beerCommentService.delete(beerCommentId));
	}
}
