package be.global.statistics.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import be.global.statistics.entity.TotalStatistics;

public interface TotalStatisticsRepository extends JpaRepository<TotalStatistics, Long> {
}
