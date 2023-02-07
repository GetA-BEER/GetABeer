package be.domain.rating.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import be.domain.rating.entity.RatingTag;

public interface RatingTagRepository extends JpaRepository<RatingTag, Long> {
}
