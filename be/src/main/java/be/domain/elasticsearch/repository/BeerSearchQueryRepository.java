package be.domain.elasticsearch.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.stereotype.Component;

import be.domain.elasticsearch.entity.BeerDocument;
import be.domain.elasticsearch.dto.SearchParam;
import lombok.RequiredArgsConstructor;

// @Component
@RequiredArgsConstructor
public class BeerSearchQueryRepository {
	private final ElasticsearchOperations elasticsearchOperations;

	public List<BeerDocument> findByCondition(SearchParam searchParam, Pageable pageable) {
		return null;
	}

	public List<BeerDocument> findByName(String name, Pageable pageable) {
		CriteriaQuery criteriaQuery = new CriteriaQuery(new Criteria());

		criteriaQuery.addCriteria(Criteria.where("korName").is(name)
			.or(Criteria.where("engName").is(name))).setPageable(pageable);

		SearchHits<BeerDocument> searchHits = elasticsearchOperations.search(criteriaQuery, BeerDocument.class);

		return searchHits.stream()
			.map(SearchHit::getContent)
			.collect(Collectors.toList());
	}
}
