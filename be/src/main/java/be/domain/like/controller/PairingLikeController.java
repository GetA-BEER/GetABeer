package be.domain.like.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import be.domain.like.dto.LikeResponseDto;
import be.domain.like.service.PairingLikeService;

@RestController
@RequestMapping("/api/pairings/likes")
public class PairingLikeController {

	private final PairingLikeService pairingLikeService;

	public PairingLikeController(PairingLikeService pairingLikeService) {
		this.pairingLikeService = pairingLikeService;
	}

	@PostMapping
	public ResponseEntity<LikeResponseDto> clickLike(@RequestParam Long pairingId) {
		LikeResponseDto response = pairingLikeService.clickLike(pairingId);

		return ResponseEntity.ok(response);
	}
}
