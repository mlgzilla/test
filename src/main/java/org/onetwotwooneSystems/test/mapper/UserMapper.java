package org.onetwotwooneSystems.test.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.onetwotwooneSystems.test.entity.UserEntity;
import org.onetwotwooneSystems.test.model.UserRequestDto;
import org.onetwotwooneSystems.test.model.UserResponseDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dailyTarget", ignore = true)
    @Mapping(target = "mealsHistory", ignore = true)
    public abstract UserEntity toUserEntity(UserRequestDto userRequestDto);

    @Mapping(target = "target", expression = "java(userEntity.getTarget().toString())")
    public abstract UserResponseDto toUserResponse(UserEntity userEntity);
}
