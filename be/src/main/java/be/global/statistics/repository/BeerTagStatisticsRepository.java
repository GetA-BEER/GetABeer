package be.global.statistics.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import be.global.statistics.entity.BeerTagStatistics;

public interface BeerTagStatisticsRepository extends JpaRepository<BeerTagStatistics, Long> {
}
