package be.domain.beerTag.repository;

import be.domain.beerTag.entity.BeerTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BeerTagRepository extends JpaRepository<BeerTag, Long> {
}
