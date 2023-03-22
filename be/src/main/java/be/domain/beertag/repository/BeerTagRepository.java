package be.domain.beertag.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import be.domain.beertag.entity.BeerTag;
import be.domain.beertag.entity.BeerTagType;

public interface BeerTagRepository extends JpaRepository<BeerTag, Long> {

	Optional<BeerTag> findBeerTagByBeerTagType(BeerTagType beerTagType);
}
