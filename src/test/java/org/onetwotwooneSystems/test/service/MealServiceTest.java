package org.onetwotwooneSystems.test.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.onetwotwooneSystems.test.TestApplicationTests;
import org.onetwotwooneSystems.test.entity.MealEntity;
import org.onetwotwooneSystems.test.entity.MealsHistoryEntity;
import org.onetwotwooneSystems.test.entity.UserEntity;
import org.onetwotwooneSystems.test.enums.UserTarget;
import org.onetwotwooneSystems.test.mapper.MealMapper;
import org.onetwotwooneSystems.test.model.MealHistoryRequestDto;
import org.onetwotwooneSystems.test.model.MealHistoryRequestMealListInnerDto;
import org.onetwotwooneSystems.test.model.MealRequestDto;
import org.onetwotwooneSystems.test.model.MealResponseDto;
import org.onetwotwooneSystems.test.repository.MealRepository;
import org.onetwotwooneSystems.test.repository.MealsHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class MealServiceTest extends TestApplicationTests {

    private static final String FULL_MEAL_HISTORY = "История приемов пищи по дням:\n" +
            "2025-03-27\n" +
            "Шаурма 2\n" +
            "Бургер 2\n" +
            "2025-03-26\n" +
            "Салат 2\n";
    private static final String TODAY_MEAL_HISTORY = "История приемов пищи сегодня:\n" +
            "Шаурма 2\n" +
            "Бургер 2\n";
    private static final String EMPTY_MEAL_HISTORY = "Ваша история приемов пищи пуста.";
    private static final String EMPTY_MEAL_HISTORY_TODAY = "Ваша история приемов пищи сегодня пуста.";

    @Autowired
    private MealService mealService;

    @Autowired
    private MealMapper mealMapper;

    @MockitoBean
    private MealRepository mealRepository;

    @MockitoBean
    private MealRepository userRepository;
    @MockitoBean
    private MealsHistoryRepository mealsHistoryRepository;

    private MealRequestDto mealRequestDto;
    private MealResponseDto mealResponseDto;
    private MealResponseDto mealResponseDto2;
    private MealEntity mealEntity;
    private MealEntity mealEntity2;
    private MealEntity mealEntity3;
    private MealsHistoryEntity mealsHistoryEntity;
    private MealsHistoryEntity mealsHistoryEntity2;
    private MealsHistoryEntity mealsHistoryEntity3;
    private UserEntity userEntity;
    private MealHistoryRequestDto mealHistoryRequestDto;

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
                .id(UUID.fromString("cb570e9f-6709-4586-90b1-6a6d2cc15b37"))
                .name("Шаурма")
                .calories(200)
                .proteins(12)
                .fats(10)
                .carbohydrates(15)
                .build();
        mealEntity2 = MealEntity.builder()
                .id(UUID.fromString("4116f63d-21a8-445e-8c1f-6ef7dc937fb0"))
                .name("Бургер")
                .calories(300)
                .proteins(15)
                .fats(20)
                .carbohydrates(35)
                .build();
        mealEntity3 = MealEntity.builder()
                .id(UUID.fromString("abb3ed0d-012b-43b3-94b3-bb60acf3da71"))
                .name("Салат")
                .calories(100)
                .proteins(5)
                .fats(1)
                .carbohydrates(1)
                .build();
        mealResponseDto = MealResponseDto.builder()
                .id(UUID.fromString("cb570e9f-6709-4586-90b1-6a6d2cc15b37"))
                .name("Шаурма")
                .calories(200)
                .proteins(12)
                .fats(10)
                .carbohydrates(15)
                .build();
        mealResponseDto2 = MealResponseDto.builder()
                .id(UUID.fromString("4116f63d-21a8-445e-8c1f-6ef7dc937fb0"))
                .name("Бургер")
                .calories(300)
                .proteins(15)
                .fats(20)
                .carbohydrates(35)
                .build();
        mealsHistoryEntity = MealsHistoryEntity.builder()
                .id(UUID.fromString("5e374669-3448-40dc-bb0a-62ecf71b1b0d"))
                .userEntity(userEntity)
                .mealEntity(mealEntity)
                .mealAmount(2)
                .dateTime(LocalDateTime.of(2025, 3, 27, 12, 16, 35))
                .build();
        mealsHistoryEntity2 = MealsHistoryEntity.builder()
                .id(UUID.fromString("6474921c-0745-4273-b4b3-015526af0eb3"))
                .userEntity(userEntity)
                .mealEntity(mealEntity2)
                .mealAmount(2)
                .dateTime(LocalDateTime.of(2025, 3, 27, 12, 16, 35))
                .build();
        mealsHistoryEntity3 = MealsHistoryEntity.builder()
                .id(UUID.fromString("63dd554d-5fc5-454d-8bdb-95e8e685e8b2"))
                .userEntity(userEntity)
                .mealEntity(mealEntity3)
                .mealAmount(2)
                .dateTime(LocalDateTime.of(2025, 3, 26, 12, 16, 35))
                .build();
        mealHistoryRequestDto = MealHistoryRequestDto.builder()
                .userId(UUID.fromString("cb570e9f-6709-4586-90b1-6a6d2cc15b37"))
                .mealList(List.of(MealHistoryRequestMealListInnerDto.builder()
                        .mealId(UUID.fromString("cb570e9f-6709-4586-90b1-6a6d2cc15b37"))
                        .mealAmount(2)
                        .build()))
                .build();
    }

    @Test
    void createMealTest() {
        when(mealRepository.save(notNull())).thenReturn(mealEntity);
        MealResponseDto meal = mealService.createMeal(mealRequestDto);
        assertTrue(new ReflectionEquals(mealResponseDto).matches(meal));
    }

    @Test
    void getMealByIdTest() {
        when(mealRepository.findById(UUID.fromString("cb570e9f-6709-4586-90b1-6a6d2cc15b37")))
                .thenReturn(Optional.ofNullable(mealEntity));
        MealResponseDto mealById = mealService.getMealById("cb570e9f-6709-4586-90b1-6a6d2cc15b37");
        assertTrue(new ReflectionEquals(mealResponseDto).matches(mealById));
    }

    @Test
    void getAllMealsTest() {
        when(mealRepository.findAll()).thenReturn(List.of(mealEntity, mealEntity2));
        List<MealResponseDto> allMeals = mealService.getAllMeals();
        List<MealResponseDto> responseDtos = List.of(mealResponseDto, mealResponseDto2);
        for (int i = 0; i < responseDtos.size(); i++) {
            assertTrue(new ReflectionEquals(responseDtos.get(i)).matches(allMeals.get(i)));
        }
    }

    @Test
    void getMealHistoryTest() {
        when(mealsHistoryRepository.findAllByUserId(UUID.fromString("2df05112-e154-4b69-be18-7b762b3c94c8")))
                .thenReturn(List.of(mealsHistoryEntity, mealsHistoryEntity2, mealsHistoryEntity3));
        String mealHistory = mealService.getMealHistory("2df05112-e154-4b69-be18-7b762b3c94c8");
        assertEquals(FULL_MEAL_HISTORY, mealHistory);
    }

    @Test
    void getMealHistoryEmptyTest() {
        when(mealsHistoryRepository.findAllByUserId(UUID.fromString("2df05112-e154-4b69-be18-7b762b3c94c8")))
                .thenReturn(List.of());
        String mealHistory = mealService.getMealHistory("2df05112-e154-4b69-be18-7b762b3c94c8");
        assertEquals(EMPTY_MEAL_HISTORY, mealHistory);
    }

    @Test
    void getMealHistoryTodayTest() {
        when(mealsHistoryRepository.findAllByUserIdAndDate(
                eq(UUID.fromString("2df05112-e154-4b69-be18-7b762b3c94c8")), any()))
                .thenReturn(List.of(mealsHistoryEntity, mealsHistoryEntity2));
        String mealHistoryToday = mealService.getMealHistoryToday("2df05112-e154-4b69-be18-7b762b3c94c8");
        assertEquals(TODAY_MEAL_HISTORY, mealHistoryToday);
    }

    @Test
    void getMealHistoryTodayEmptyTest() {
        when(mealsHistoryRepository.findAllByUserIdAndDate(
                eq(UUID.fromString("2df05112-e154-4b69-be18-7b762b3c94c8")), any()))
                .thenReturn(List.of());
        String mealHistoryToday = mealService.getMealHistoryToday("2df05112-e154-4b69-be18-7b762b3c94c8");
        assertEquals(EMPTY_MEAL_HISTORY_TODAY, mealHistoryToday);
    }
}
