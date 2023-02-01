package be.domain.pairing.controller;

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
import org.springframework.web.bind.annotation.RestController;

import be.domain.pairing.dto.PairingDto;
import be.domain.pairing.dto.PairingImageDto;
import be.domain.pairing.entity.Pairing;
import be.domain.pairing.entity.Pairing2;
import be.domain.pairing.mapper.PairingMapper;
import be.domain.pairing.mapper.PairingMapper2;
import be.domain.pairing.service.PairingService;
import be.domain.pairing.service.PairingService2;

@RestController
@RequestMapping("/pairing/v2")
public class PairingController2 {
	private final PairingService2 pairingService;
	private final PairingMapper2 mapper;

	public PairingController2(PairingService2 pairingService, PairingMapper2 mapper) {
		this.pairingService = pairingService;
		this.mapper = mapper;
	}

	/* 페어링 등록 v2 */
	@PostMapping
	public ResponseEntity<PairingDto.Response2> post(@RequestBody PairingDto.Post2 post2) {
		Pairing2 pairing = pairingService.create(mapper.pairingPost2DtoToPairing2(post2), post2.getImage(),
			post2.getCategory());
		List<PairingImageDto.Response2> response2 = pairingService.getImageDtoList(pairing.getId());

		return ResponseEntity.status(HttpStatus.CREATED)
			.body(mapper.pairingToPairingResponseDto(pairing, response2));
	}

	/* 페어링 수정 v2 */
	@PatchMapping("/{pairingId}")
	public ResponseEntity<PairingDto.Response2> patch(@PathVariable @Positive Long pairingId,
		@RequestBody PairingDto.Patch2 patch) {

		Pairing2 pairing = pairingService.update(mapper.pairingPatchDtoToPairing(patch), pairingId, patch.getCategory(),
			patch.getImage());

		List<PairingImageDto.Response2> response2 = pairingService.getImageDtoList(pairing.getId());

		return ResponseEntity.ok(mapper.pairingToPairingResponseDto(pairing, response2));
	}

	/* 특정 페어링 상세 조회 */
	@GetMapping("/{pairingId}")
	public ResponseEntity<PairingDto.Response2> getPairing(@PathVariable @Positive Long pairingId) {
		Pairing2 pairing = pairingService.getPairing(pairingId);
		List<PairingImageDto.Response2> response2 = pairingService.getImageDtoList(pairing.getId());

		return ResponseEntity.ok(mapper.pairingToPairingResponseDto(pairing, response2));
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
