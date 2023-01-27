package be.domain.recomment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import be.domain.recomment.entity.PairingRecomment;

@Repository
public interface PairingRecommentRepository extends JpaRepository<PairingRecomment, Long> {
}
