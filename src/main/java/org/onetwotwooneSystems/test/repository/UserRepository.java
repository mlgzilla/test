package org.onetwotwooneSystems.test.repository;

import org.onetwotwooneSystems.test.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findById(UUID id);

    @Query("select u.dailyTarget from UserEntity u where u.id = ?1")
    Optional<Integer> getDailyTargetByUserId(UUID id);

    @Modifying
    @Query("update UserEntity set dailyTarget = 10 * weight + cast(6.25 * height as int) - 5 * age " +
            "where target = org.onetwotwooneSystems.test.enums.UserTarget.MAINTAIN")
    void resetDailyTargetMaintain();

    @Modifying
    @Query("update UserEntity set dailyTarget = 10 * weight + cast(6.25 * height as int) - 5 * age - 250 " +
            "where target = org.onetwotwooneSystems.test.enums.UserTarget.LOSS")
    void resetDailyTargetLoss();

    @Modifying
    @Query("update UserEntity set dailyTarget = 10 * weight + cast(6.25 * height as int) - 5 * age + 250 " +
            "where target = org.onetwotwooneSystems.test.enums.UserTarget.GAIN")
    void resetDailyTargetGain();

    @Modifying
    @Query("update UserEntity set dailyTarget = ?1 where id = ?2")
    void updateDailyTarget(Integer newDailyTarget, UUID userId);
}
