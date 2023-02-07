package be.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import be.domain.user.entity.UserBeerCategory;

public interface UserBeerCategoryRepository extends JpaRepository<UserBeerCategory, Long> {
}
