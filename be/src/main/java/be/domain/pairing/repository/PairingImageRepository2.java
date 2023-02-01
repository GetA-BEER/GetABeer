package be.domain.pairing.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import be.domain.pairing.entity.PairingImage;
import be.domain.pairing.entity.PairingImage2;

public interface PairingImageRepository2 extends JpaRepository<PairingImage2, Long> {
}
