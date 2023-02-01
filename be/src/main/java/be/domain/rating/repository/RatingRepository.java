package be.domain.rating.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import be.domain.rating.entity.Rating;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long>, RatingCustomRepository {
}
