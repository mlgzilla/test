package org.onetwotwooneSystems.test.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.onetwotwooneSystems.test.entity.MealEntity;
import org.onetwotwooneSystems.test.entity.MealsHistoryEntity;
import org.onetwotwooneSystems.test.entity.UserEntity;
import org.onetwotwooneSystems.test.model.MealRequestDto;
import org.onetwotwooneSystems.test.model.MealResponseDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class MealMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "mealsHistoryEntity", ignore = true)
    public abstract MealEntity toMealEntity(MealRequestDto mealRequestDto);

    public abstract MealResponseDto toMealResponse(MealEntity mealEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateTime", expression = "java(java.time.LocalDateTime.now())")
    public abstract MealsHistoryEntity toMealsHistoryEntity(Integer mealAmount, UserEntity userEntity, MealEntity mealEntity);
}
