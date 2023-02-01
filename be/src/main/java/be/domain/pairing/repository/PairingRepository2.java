package be.domain.pairing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import be.domain.pairing.entity.Pairing;
import be.domain.pairing.entity.Pairing2;

@Repository
public interface PairingRepository2 extends JpaRepository<Pairing2, Long>, PairingCustomRepository {
}
