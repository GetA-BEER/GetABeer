package be.domain.pairing.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import be.domain.beer.entity.Beer;
import be.domain.beer.service.BeerService;
import be.domain.pairing.dto.PairingRequestDto;
import be.domain.pairing.dto.PairingResponseDto;
import be.domain.pairing.mapper.PairingMapper;
import be.domain.pairing.service.PairingService;
import be.global.dto.MultiResponseDto;
import be.global.dto.MultiResponseDtoWithBeerInfo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Validated
@RestController
@RequestMapping("/api/pairings")
public class PairingController {
	private final PairingService pairingService;
	private final BeerService beerService;
	private final PairingMapper mapper;

	public PairingController(PairingService pairingService, BeerService beerService, PairingMapper mapper) {
		this.pairingService = pairingService;
		this.beerService = beerService;
		this.mapper = mapper;
	}

	/* 페어링 등록 */
	@PostMapping
	public ResponseEntity<String> post(@RequestPart(value = "post") @Valid PairingRequestDto.Post post,
		@RequestPart(value = "files", required = false) List<MultipartFile> files) throws IOException {
		log.info("************************************************************");
		log.info("여기는 컨트롤러");
		log.info("내용이 잘 들어오나 : " + post.getContent());
		log.info("post 내용 확인 : " + post.getCategory());
		log.info("************************************************************");
		String message = pairingService.create(mapper.pairingPostDtoToPairing(post),
			files, post.getBeerId());

		return ResponseEntity.status(HttpStatus.CREATED).body(message);
	}

	/* 페어링 수정 */
	@PatchMapping("/{pairingId}")
	public ResponseEntity<String> patch(@PathVariable @Positive Long pairingId,
		@RequestPart(value = "files", required = false) List<MultipartFile> files,
		@RequestPart(value = "patch") @Valid PairingRequestDto.Patch patch) throws IOException {
		String message = pairingService.update(mapper.pairingPatchDtoToPairing(patch),
			pairingId, patch.getType(), patch.getUrl(), files);

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
	@GetMapping("/page/{type}/{category}")
	public ResponseEntity<MultiResponseDtoWithBeerInfo<PairingResponseDto.Total>> getPairingPageOrderByRecent(
		@PathVariable String type, @PathVariable String category,
		@RequestParam Long beerId, @RequestParam Integer page, @RequestParam Integer size) {
		Page<PairingResponseDto.Total> responses =
			pairingService.getPairingPageOrderBy(beerId, type, category, page, size);
		Beer beer = beerService.getBeer(beerId);

		return ResponseEntity.ok(
			new MultiResponseDtoWithBeerInfo<PairingResponseDto.Total>(responses.getContent(), responses, beer)
		);
	}
}
