package org.onetwotwooneSystems.test.service;

import org.onetwotwooneSystems.test.model.MealHistoryRequestDto;
import org.onetwotwooneSystems.test.model.MealRequestDto;
import org.onetwotwooneSystems.test.model.MealResponseDto;

import java.util.List;

public interface MealService {
    MealResponseDto createMeal(MealRequestDto mealRequestDto);

    void createMealHistory(MealHistoryRequestDto mealHistoryRequestDto);

    MealResponseDto getMealById(String id);

    List<MealResponseDto> getAllMeals();

    String getMealHistory(String id);

    String getMealHistoryToday(String id);
}
