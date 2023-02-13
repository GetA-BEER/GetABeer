package be.domain.like.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import be.domain.like.entity.PairingLike;

public interface PairingLikeRepository extends JpaRepository<PairingLike, Long>, PairingLikeCustomRepository {
}
