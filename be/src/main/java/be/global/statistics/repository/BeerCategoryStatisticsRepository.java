package be.global.statistics.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import be.global.statistics.entity.BeerCategoryStatistics;

public interface BeerCategoryStatisticsRepository extends JpaRepository<BeerCategoryStatistics, Long> {
}
