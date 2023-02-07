package be.domain.crawler.javaCrawler;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CrawledInfoRepository extends JpaRepository<CrawledInfo, Long> {
}
