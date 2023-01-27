package be.domain.pairing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import be.domain.pairing.entity.Pairing;

@Repository
public interface PairingRepository extends JpaRepository<Pairing, Long>, PairingCustomRepository {
}
