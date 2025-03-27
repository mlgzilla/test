package org.onetwotwooneSystems.test.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.onetwotwooneSystems.test.TestApplicationTests;
import org.onetwotwooneSystems.test.entity.MealEntity;
import org.onetwotwooneSystems.test.entity.MealsHistoryEntity;
import org.onetwotwooneSystems.test.entity.UserEntity;
import org.onetwotwooneSystems.test.enums.UserTarget;
import org.onetwotwooneSystems.test.model.MealRequestDto;
import org.onetwotwooneSystems.test.model.MealResponseDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class MealMapperTest extends TestApplicationTests {

    @Autowired
    private MealMapper mealMapper;
    private MealRequestDto mealRequestDto;
    private MealResponseDto mealResponseDto;
    private MealEntity mealEntity;
    private MealsHistoryEntity mealsHistoryEntity;
    private UserEntity userEntity;

    @BeforeEach
    void init() {
        userEntity = UserEntity.builder()
                .id(UUID.fromString("2df05112-e154-4b69-be18-7b762b3c94c8"))
                .name("Иван")
                .email("Ivan@mail.ru")
                .age(25)
                .weight(80)
                .height(180)
                .target(UserTarget.LOSS)
                .dailyTarget(1500)
                .build();
        mealRequestDto = MealRequestDto.builder()
                .name("Шаурма")
                .calories(200)
                .proteins(12)
                .fats(10)
                .carbohydrates(15)
                .build();
        mealEntity = MealEntity.builder()
                .name("Шаурма")
                .calories(200)
                .proteins(12)
                .fats(10)
                .carbohydrates(15)
                .build();
        mealResponseDto = MealResponseDto.builder()
                .id(UUID.fromString("cb570e9f-6709-4586-90b1-6a6d2cc15b37"))
                .name("Шаурма")
                .calories(200)
                .proteins(12)
                .fats(10)
                .carbohydrates(15)
                .build();
        mealsHistoryEntity = MealsHistoryEntity.builder()
                .userEntity(userEntity)
                .mealAmount(2)
                .build();
    }

    @Test
    void toMealEntityTest() {
        MealEntity mappedMealEntity = mealMapper.toMealEntity(mealRequestDto);
        assertTrue(new ReflectionEquals(mealEntity).matches(mappedMealEntity));
    }

    @Test
    void toMealResponseTest() {
        mealEntity.setId(UUID.fromString("cb570e9f-6709-4586-90b1-6a6d2cc15b37"));
        MealResponseDto mappedMealResponse = mealMapper.toMealResponse(mealEntity);
        assertTrue(new ReflectionEquals(mealResponseDto).matches(mappedMealResponse));
    }

    @Test
    void toMealsHistoryEntityTest() {
        mealEntity.setId(UUID.fromString("cb570e9f-6709-4586-90b1-6a6d2cc15b37"));
        mealsHistoryEntity.setMealEntity(mealEntity);
        MealsHistoryEntity mappedMealsHistoryEntity = mealMapper.toMealsHistoryEntity(2, userEntity, mealEntity);
        assertTrue(new ReflectionEquals(mealsHistoryEntity, "dateTime").matches(mappedMealsHistoryEntity));
    }
}
