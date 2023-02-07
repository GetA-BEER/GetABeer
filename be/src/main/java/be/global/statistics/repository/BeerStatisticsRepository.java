package be.global.statistics.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import be.global.statistics.entity.BeerStatistics;

public interface BeerStatisticsRepository extends JpaRepository<BeerStatistics, Long> {
}
