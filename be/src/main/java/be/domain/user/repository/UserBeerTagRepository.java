package be.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import be.domain.user.entity.UserBeerTag;

public interface UserBeerTagRepository extends JpaRepository<UserBeerTag, Long> {
}
