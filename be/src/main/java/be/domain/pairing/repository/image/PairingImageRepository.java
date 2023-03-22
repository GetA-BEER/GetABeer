package be.domain.pairing.repository.image;

import org.springframework.data.jpa.repository.JpaRepository;

import be.domain.pairing.entity.PairingImage;

public interface PairingImageRepository extends JpaRepository<PairingImage, Long>, PairingImageCustomRepository {
}
