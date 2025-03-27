package org.onetwotwooneSystems.test.service.impl;

import lombok.RequiredArgsConstructor;
import org.onetwotwooneSystems.test.entity.MealEntity;
import org.onetwotwooneSystems.test.entity.MealsHistoryEntity;
import org.onetwotwooneSystems.test.entity.UserEntity;
import org.onetwotwooneSystems.test.exception.supplier.ExceptionSupplier;
import org.onetwotwooneSystems.test.mapper.MealMapper;
import org.onetwotwooneSystems.test.model.MealHistoryRequestDto;
import org.onetwotwooneSystems.test.model.MealRequestDto;
import org.onetwotwooneSystems.test.model.MealResponseDto;
import org.onetwotwooneSystems.test.repository.MealRepository;
import org.onetwotwooneSystems.test.repository.MealsHistoryRepository;
import org.onetwotwooneSystems.test.repository.UserRepository;
import org.onetwotwooneSystems.test.service.MealService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MealServiceImpl implements MealService {

    private static final String EMPTY_MEAL_HISTORY = "Ваша история приемов пищи пуста.";
    private static final String EMPTY_MEAL_HISTORY_TODAY = "Ваша история приемов пищи сегодня пуста.";
    private static final String MEAL_HISTORY_START = "История приемов пищи по дням:\n";
    private static final String MEAL_HISTORY_START_TODAY = "История приемов пищи сегодня:\n";

    private final MealRepository mealRepository;
    private final MealsHistoryRepository mealsHistoryRepository;
    private final UserRepository userRepository;
    private final MealMapper mealMapper;
    private final ExceptionSupplier exceptionSupplier;

    @Override
    @Transactional
    public MealResponseDto createMeal(MealRequestDto mealRequestDto) {
        MealEntity mappedMealEntity = mealMapper.toMealEntity(mealRequestDto);
        MealEntity savedUserEntity = mealRepository.save(mappedMealEntity);
        return mealMapper.toMealResponse(savedUserEntity);
    }

    @Override
    @Transactional
    public void createMealHistory(MealHistoryRequestDto mealHistoryRequestDto) {
        UserEntity user = userRepository.findById(mealHistoryRequestDto.getUserId())
                .orElseThrow(exceptionSupplier.userNotFound(mealHistoryRequestDto.getUserId().toString()));
        List<MealsHistoryEntity> mappedMealsHistory = mealHistoryRequestDto.getMealList().stream()
                .map(mealHistory ->
                        mealMapper.toMealsHistoryEntity(
                                mealHistory.getMealAmount(),
                                user,
                                mealRepository.findById(mealHistory.getMealId())
                                        .orElseThrow(exceptionSupplier
                                                .mealNotFound(mealHistory.getMealId().toString()))))
                .toList();
        List<MealsHistoryEntity> savedMealsHistory = mealsHistoryRepository.saveAll(mappedMealsHistory);
        Integer totalCaloriesAmount = savedMealsHistory.stream()
                .mapToInt(mealHistory -> mealHistory.getMealAmount() * mealHistory.getMealEntity().getCalories())
                .sum();
        userRepository.updateDailyTarget(totalCaloriesAmount, user.getId());
    }


    @Override
    @Transactional(readOnly = true)
    public MealResponseDto getMealById(String id) {
        MealEntity mealEntity = mealRepository.findById(UUID.fromString(id))
                .orElseThrow(exceptionSupplier.mealNotFound(id));
        return mealMapper.toMealResponse(mealEntity);
    }

    @Override
    public List<MealResponseDto> getAllMeals() {
        List<MealEntity> allMeals = mealRepository.findAll();
        return allMeals.stream()
                .map(mealMapper::toMealResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public String getMealHistory(String id) {
        List<MealsHistoryEntity> mealsHistory = mealsHistoryRepository.findAllByUserId(UUID.fromString(id));
        if (mealsHistory.isEmpty())
            return EMPTY_MEAL_HISTORY;
        else {
            Map<LocalDate, List<MealsHistoryEntity>> dailyMealsHistory = mealsHistory.stream()
                    .collect(Collectors.groupingBy(meal -> LocalDate.from(meal.getDateTime())));
            StringBuffer mealHistoryResponse = new StringBuffer(MEAL_HISTORY_START);
            for (Map.Entry<LocalDate, List<MealsHistoryEntity>> entry : dailyMealsHistory.entrySet()) {
                mealHistoryResponse
                        .append("\n")
                        .append(entry.getKey())
                        .append("\n");
                for (MealsHistoryEntity meal : entry.getValue()) {
                    mealHistoryResponse
                            .append(meal.getMealEntity().getName())
                            .append(", кол-во: ")
                            .append(meal.getMealAmount())
                            .append("\n");
                }
            }
            return mealHistoryResponse.toString();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public String getMealHistoryToday(String id) {
        List<MealsHistoryEntity> todaysMealHistory = mealsHistoryRepository.findAllByUserIdAndDate(UUID.fromString(id), LocalDateTime.now());
        if (todaysMealHistory.isEmpty())
            return EMPTY_MEAL_HISTORY_TODAY;
        else {
            StringBuffer mealHistoryResponse = new StringBuffer(MEAL_HISTORY_START_TODAY);
            for (MealsHistoryEntity meal : todaysMealHistory) {
                mealHistoryResponse
                        .append(meal.getMealEntity().getName())
                        .append(", кол-во: ")
                        .append(meal.getMealAmount())
                        .append("\n");
            }
            return mealHistoryResponse.toString();
        }
    }
}
