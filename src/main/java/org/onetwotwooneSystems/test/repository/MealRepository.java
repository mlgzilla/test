package org.onetwotwooneSystems.test.repository;

import org.onetwotwooneSystems.test.entity.MealEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MealRepository extends JpaRepository<MealEntity, UUID> {

    Optional<MealEntity> findById(UUID id);

    List<MealEntity> findAll();
}
