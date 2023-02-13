package be.domain.like.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import be.domain.like.entity.RatingLike;

public interface RatingLikeRepository extends JpaRepository<RatingLike, Long>, RatingLikeCustomRepository {
}
