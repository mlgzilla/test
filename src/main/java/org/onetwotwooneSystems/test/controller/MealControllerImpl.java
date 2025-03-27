package org.onetwotwooneSystems.test.controller;

import lombok.RequiredArgsConstructor;
import org.onetwotwooneSystems.test.MealApi;
import org.onetwotwooneSystems.test.model.MealHistoryRequestDto;
import org.onetwotwooneSystems.test.model.MealRequestDto;
import org.onetwotwooneSystems.test.model.MealResponseDto;
import org.onetwotwooneSystems.test.service.MealService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MealControllerImpl implements MealApi {

    private final MealService mealService;

    @Override
    public ResponseEntity<MealResponseDto> createMeal(MealRequestDto mealRequestDto) {
        MealResponseDto meal = mealService.createMeal(mealRequestDto);
        return ResponseEntity.ok(meal);
    }

    @Override
    public ResponseEntity<Void> createMealHistory(MealHistoryRequestDto mealHistoryRequestDto) {
        mealService.createMealHistory(mealHistoryRequestDto);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<MealResponseDto>> getAllMeals() {
        List<MealResponseDto> meals = mealService.getAllMeals();
        return ResponseEntity.ok(meals);
    }

    @Override
    public ResponseEntity<MealResponseDto> getMealById(String id) {
        MealResponseDto mealById = mealService.getMealById(id);
        return ResponseEntity.ok(mealById);
    }

    @Override
    public ResponseEntity<String> getMealHistory(String id) {
        String mealHistory = mealService.getMealHistory(id);
        return ResponseEntity.ok(mealHistory);
    }

    @Override
    public ResponseEntity<String> getMealHistoryToday(String id) {
        String mealHistory = mealService.getMealHistoryToday(id);
        return ResponseEntity.ok(mealHistory);
    }
}
