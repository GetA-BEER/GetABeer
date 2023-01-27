package be.domain.beertag.repository;

import be.domain.beertag.entity.BeerTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BeerTagRepository extends JpaRepository<BeerTag, Long> {
}
