package be.domain.elasticsearch.service;

import java.util.List;
import java.util.UUID;

import org.elasticsearch.client.ElasticsearchClient;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Service;

import be.domain.elasticsearch.entity.EsAutoComplete;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EsAutoCompleteService {

	private final ElasticsearchOperations elasticsearchOperations;

	/*
	 * 인덱스 생성 메서드
	 */
	public String createIndex() {

		IndexCoordinates indexCoordinates = elasticsearchOperations.getIndexCoordinatesFor(EsAutoComplete.class);

		IndexQuery indexQuery = new IndexQueryBuilder()
			.withId(UUID.randomUUID().toString())
			.withObject(new EsAutoComplete())
			.build();

		return elasticsearchOperations.index(indexQuery, indexCoordinates);
	}

	/*
	 * Document 삽입 메서드
	 */
	public List<EsAutoComplete> insertDocument(List<EsAutoComplete> esAutoCompleteList) {
		return (List<EsAutoComplete>)elasticsearchOperations.save(esAutoCompleteList);
	}
}
