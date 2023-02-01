package be.domain.beertag.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import be.domain.beertag.entity.BeerTag;

public interface BeerTagRepository extends JpaRepository<BeerTag, Long> {
}
