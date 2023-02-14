package be.domain.pairing.controller;

import java.io.IOException;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
	public ResponseEntity<String> post(@RequestPart(name = "post") PairingRequestDto.Post post,
		@RequestPart(name = "files", required = false) List<MultipartFile> files) throws IOException {
		String message = pairingService.create(mapper.pairingPostDtoToPairing(post),
			files, post.getCategory(), post.getBeerId(), post.getUserId());

		return ResponseEntity.status(HttpStatus.CREATED).body(message);
	}

	/* 페어링 수정 */
	@PatchMapping("/{pairingId}")
	public ResponseEntity<String> patch(@PathVariable @Positive Long pairingId,
		@RequestBody PairingRequestDto.Patch patch) {
		String message = pairingService.update(mapper.pairingPatchDtoToPairing(patch),
			pairingId, patch.getCategory(), patch.getImageUrl());

		return ResponseEntity.ok(message);
	}

	/* 특정 페어링 상세 조회 */
	@GetMapping("/{pairingId}")
	public ResponseEntity<PairingResponseDto.Detail> getPairing(@PathVariable @Positive Long pairingId) {
		PairingResponseDto.Detail response = pairingService.getPairing(pairingId);

		return ResponseEntity.ok(response);
	}

	/* 페어링 삭제 */
	@DeleteMapping("/{pairingId}")
	public ResponseEntity<String> delete(@PathVariable @Positive Long pairingId) {

		return ResponseEntity.ok(pairingService.delete(pairingId));
	}

	/* 페어링 페이지 조회 */
	@GetMapping("/page/{type}")
	public ResponseEntity<MultiResponseDto<PairingResponseDto.Total>> getPairingPageOrderByRecent(
		@PathVariable String type, @RequestParam Long beerId, @RequestParam Integer page, @RequestParam Integer size) {
		Page<PairingResponseDto.Total> responses = pairingService.getPairingPageOrderByRecent(beerId, page, size, type);

		return ResponseEntity.ok(new MultiResponseDto<>(responses.getContent(), responses));
	}
}
