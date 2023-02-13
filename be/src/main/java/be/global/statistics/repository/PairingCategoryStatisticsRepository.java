package be.global.statistics.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import be.global.statistics.entity.PairingCategoryStatistics;

public interface PairingCategoryStatisticsRepository extends JpaRepository<PairingCategoryStatistics, Long> {
}
