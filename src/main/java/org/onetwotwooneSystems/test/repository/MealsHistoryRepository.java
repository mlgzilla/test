package org.onetwotwooneSystems.test.repository;

import org.onetwotwooneSystems.test.entity.MealsHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface MealsHistoryRepository extends JpaRepository<MealsHistoryEntity, UUID> {

    @Query("select m from MealsHistoryEntity m where m.userEntity.id = ?1 order by m.dateTime")
    List<MealsHistoryEntity> findAllByUserId(UUID userId);

    @Query("select m from MealsHistoryEntity m where m.userEntity.id = ?1 and cast(?2 as date) = cast(m.dateTime as date) order by m.dateTime")
    List<MealsHistoryEntity> findAllByUserIdAndDate(UUID userId, LocalDateTime localDateTime);

}
