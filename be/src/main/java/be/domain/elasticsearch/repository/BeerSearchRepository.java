package be.domain.elasticsearch.repository;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import be.domain.elasticsearch.entity.BeerDocument;

public interface BeerSearchRepository extends ElasticsearchRepository<BeerDocument, Long> {

	List<BeerDocument> findByKorName(String korName);
}
