package be.domain.elasticsearch.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import be.domain.elasticsearch.entity.BeerDocument;
import be.domain.elasticsearch.service.ElasticsearchService;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequestMapping({"/es", "/api/search/es"})
@RequiredArgsConstructor
public class EsController {
	private final ElasticsearchService elasticsearchService;

	@GetMapping
	public ResponseEntity<List<BeerDocument>> test(@RequestParam(value = "name") String name) {
		return ResponseEntity.ok(elasticsearchService.searchByName(name));
	}

	@PostMapping("/create")
	public ResponseEntity<String> createIndex() {
		return ResponseEntity.ok(elasticsearchService.createIndex());
	}

	@PostMapping("/saveall")
	public ResponseEntity<HttpStatus> saveAll() {
		elasticsearchService.saveAllBeerDocuments();
		return ResponseEntity.ok().build();
	}
}
