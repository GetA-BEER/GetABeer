package be.domain.like.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import be.domain.like.dto.LikeResponseDto;
import be.domain.like.service.RatingLikeService;

@RestController
@RequestMapping("/api/ratings/likes")
public class RatingLikeController {

	private final RatingLikeService ratingLikeService;

	public RatingLikeController(RatingLikeService ratingLikeService) {
		this.ratingLikeService = ratingLikeService;
	}

	@PostMapping
	public ResponseEntity<LikeResponseDto> clickLike(@RequestParam Long ratingId) {
		LikeResponseDto response = ratingLikeService.clickLike(ratingId);

		return ResponseEntity.ok(response);
	}
}
