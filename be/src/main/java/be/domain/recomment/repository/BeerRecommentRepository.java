package be.domain.recomment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import be.domain.recomment.entity.BeerRecomment;

@Repository
public interface BeerRecommentRepository extends JpaRepository<BeerRecomment, Long>, BeerRecommentCustomRepository {
}
