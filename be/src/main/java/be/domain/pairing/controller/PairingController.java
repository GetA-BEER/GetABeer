package be.domain.pairing.controller;

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

import be.domain.pairing.dto.PairingDto;
import be.domain.pairing.entity.Pairing;
import be.domain.pairing.mapper.PairingMapper;
import be.domain.pairing.service.PairingService;

@RestController
@RequestMapping("/pairing")
public class PairingController {
	private final PairingService pairingService;
	private final PairingMapper mapper;

	public PairingController(PairingService pairingService, PairingMapper mapper) {
		this.pairingService = pairingService;
		this.mapper = mapper;
	}

	/* 페어링 등록 */
	@PostMapping
	public ResponseEntity<PairingDto.Response> post(@RequestBody PairingDto.Post post) {
		Pairing pairing = pairingService.create(mapper.pairingPostDtoToPairing(post),
			mapper.pairingPostDtoToPairingImage(post), post.getCategory(), post.getBeerId());

		return ResponseEntity.status(HttpStatus.CREATED)
			.body(mapper.pairingToPairingResponseDto(pairing, pairing.getBeer().getId()));
	}

	/* 페어링 수정 */
	@PatchMapping("/{pairingId}")
	public ResponseEntity<PairingDto.Response> patch(@PathVariable @Positive Long pairingId,
		@RequestBody PairingDto.Patch patch) {
		Pairing pairing = pairingService.update(mapper.pairingPatchDtoToPairing(patch),
			mapper.pairingPatchDtoToPairingImage(patch), pairingId, patch.getCategory());

		return ResponseEntity.ok(mapper.pairingToPairingResponseDto(pairing, pairing.getBeer().getId()));
	}

	/* 특정 페어링 상세 조회 */
	@GetMapping("/{pairingId}")
	public ResponseEntity<PairingDto.Response> getPairing(@PathVariable @Positive Long pairingId) {
		Pairing pairing = pairingService.getPairing(pairingId);

		return ResponseEntity.ok(mapper.pairingToPairingResponseDto(pairing, pairing.getBeer().getId()));
	}

	/* 페어링 페이지 조회 */
	public ResponseEntity getPairingPage() {
		return null;
	}

	/* 페어링 삭제 */
	@DeleteMapping("/{pairingId}")
	public ResponseEntity<String> delete(@PathVariable @Positive Long pairingId) {

		return ResponseEntity.ok(pairingService.delete(pairingId));
	}
}
