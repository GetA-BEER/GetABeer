package be.domain.pairing.controller;

import javax.validation.constraints.Positive;

import org.springframework.data.domain.Page;
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

import be.domain.pairing.dto.PairingRequestDto;
import be.domain.pairing.dto.PairingResponseDto;
import be.domain.pairing.entity.Pairing;
import be.domain.pairing.mapper.PairingMapper;
import be.domain.pairing.service.PairingService;
import be.global.dto.MultiResponseDto;

@RestController
@RequestMapping("/api/pairings")
public class PairingController {
	private final PairingService pairingService;
	private final PairingMapper mapper;

	public PairingController(PairingService pairingService, PairingMapper mapper) {
		this.pairingService = pairingService;
		this.mapper = mapper;
	}

	/* 페어링 등록 */
	@PostMapping
	public ResponseEntity<PairingResponseDto.Detail> post(@RequestBody PairingRequestDto.Post post) {
		Pairing pairing = pairingService.create(mapper.pairingPostDtoToPairing(post),
			post.getImage(), post.getCategory(), post.getBeerId());

		return ResponseEntity.status(HttpStatus.CREATED)
			.body(mapper.pairingToPairingResponseDto(pairing, pairing.getBeer().getId()));
	}

	/* 페어링 수정 */
	@PatchMapping("/{pairingId}")
	public ResponseEntity<PairingResponseDto.Detail> patch(@PathVariable @Positive Long pairingId,
		@RequestBody PairingRequestDto.Patch patch) {
		Pairing pairing = pairingService.update(mapper.pairingPatchDtoToPairing(patch),
			pairingId, patch.getCategory(), patch.getImage());

		return ResponseEntity.ok(mapper.pairingToPairingResponseDto(pairing, pairing.getBeer().getId()));
	}

	/* 특정 페어링 상세 조회 */
	@GetMapping("/{pairingId}")
	public ResponseEntity<PairingResponseDto.Detail> getPairing(@PathVariable @Positive Long pairingId) {
		Pairing pairing = pairingService.getPairing(pairingId);

		return ResponseEntity.ok(mapper.pairingToPairingResponseDto(pairing, pairing.getBeer().getId()));
	}

	/* 페어링 삭제 */
	@DeleteMapping("/{pairingId}")
	public ResponseEntity<String> delete(@PathVariable @Positive Long pairingId) {

		return ResponseEntity.ok(pairingService.delete(pairingId));
	}

	//------------------------------------------ 조회 세분화 ----------------------------------------------------

	/* 페어링 페이지 조회 : 최신순 */
	@GetMapping("/recency")
	public ResponseEntity<MultiResponseDto<PairingResponseDto.Total>> getPairingPageOrderByRecent(
		@RequestParam Long beerId, @RequestParam Integer page, @RequestParam Integer size) {
		Page<PairingResponseDto.Total> responses = pairingService.getPairingPageOrderByRecent(beerId, page, size);

		return ResponseEntity.ok(new MultiResponseDto<>(responses.getContent(), responses));
	}

	/* 페어링 페이지 조회 : 추천 순*/
	@GetMapping("/mostlikes")
	public ResponseEntity<MultiResponseDto<PairingResponseDto.Total>> getPairingPageOrderByLikes(
		@RequestParam Long beerId, @RequestParam Integer page, @RequestParam Integer size) {
		Page<PairingResponseDto.Total> responses = pairingService.getPairingPageOrderByLikes(beerId, page, size);

		return ResponseEntity.ok(new MultiResponseDto<>(responses.getContent(), responses));
	}

	/* 페어링 페이지 조회 : 댓글 많은 순*/
	@GetMapping("/mostcomments")
	public ResponseEntity<MultiResponseDto<PairingResponseDto.Total>> getPairingPageOrderByComments(
		@RequestParam Long beerId, @RequestParam Integer page, @RequestParam Integer size) {
		Page<PairingResponseDto.Total> responses = pairingService.getPairingPageOrderByComments(beerId, page, size);

		return ResponseEntity.ok(new MultiResponseDto<>(responses.getContent(), responses));
	}
}
