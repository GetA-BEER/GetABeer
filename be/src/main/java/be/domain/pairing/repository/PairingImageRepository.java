package be.domain.pairing.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import be.domain.pairing.entity.PairingImage;

public interface PairingImageRepository extends JpaRepository<PairingImage, Long> {
}
