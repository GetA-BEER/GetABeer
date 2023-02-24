package be.domain.elasticsearch.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Service;

import be.domain.beer.repository.BeerRepository;
import be.domain.elasticsearch.entity.BeerDocument;
import be.domain.elasticsearch.repository.BeerSearchQueryRepository;
import be.domain.elasticsearch.repository.BeerSearchRepository;
import lombok.RequiredArgsConstructor;

// @Service
@RequiredArgsConstructor
public class ElasticsearchService {
	private final BeerRepository beerRepository;
	private final BeerSearchRepository beerSearchRepository;
	private final ElasticsearchOperations elasticsearchOperations;
	private final BeerSearchQueryRepository beerSearchQueryRepository;

	/*
	 * 인덱스 생성 메서드
	 */
	public String createIndex() {

		IndexCoordinates indexCoordinates = elasticsearchOperations.getIndexCoordinatesFor(BeerDocument.class);

		IndexQuery indexQuery = new IndexQueryBuilder()
			.withId(UUID.randomUUID().toString())
			.withObject(BeerDocument.builder().build())
			.build();

		return elasticsearchOperations.index(indexQuery, indexCoordinates);
	}

	/*
	 * Document 삽입 메서드
	 */
	public List<BeerDocument> insertDocument(List<BeerDocument> analysisBeerList) {
		return (List<BeerDocument>)elasticsearchOperations.save(analysisBeerList);
	}

	/*
	 * DB -> ES 데이터 이관 메서드
	 */
	public void saveAllBeerDocuments() {

		List<BeerDocument> beerDocumentList =
			beerRepository.findAll().stream()
				.map(BeerDocument::toEntity)
				.collect(Collectors.toList());

		beerSearchRepository.saveAll(beerDocumentList);
	}

	public List<BeerDocument> searchByName(String name) {

		PageRequest pageRequest = PageRequest.of(0, 10);

		return beerSearchQueryRepository.findByName(name, pageRequest);
	}
}
