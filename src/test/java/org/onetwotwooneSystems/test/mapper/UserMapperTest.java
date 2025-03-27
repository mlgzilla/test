package org.onetwotwooneSystems.test.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.onetwotwooneSystems.test.TestApplicationTests;
import org.onetwotwooneSystems.test.entity.UserEntity;
import org.onetwotwooneSystems.test.enums.UserTarget;
import org.onetwotwooneSystems.test.model.UserRequestDto;
import org.onetwotwooneSystems.test.model.UserResponseDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserMapperTest extends TestApplicationTests {
    @Autowired
    private UserMapper userMapper;
    private UserRequestDto userRequestDto;
    private UserResponseDto userResponseDto;
    private UserEntity userEntity;

    @BeforeEach
    void init() {
        userRequestDto = UserRequestDto.builder()
                .name("Иван")
                .email("Ivan@mail.ru")
                .age(25)
                .weight(80)
                .height(180)
                .target("LOSS")
                .build();
        userEntity = UserEntity.builder()
                .name("Иван")
                .email("Ivan@mail.ru")
                .age(25)
                .weight(80)
                .height(180)
                .target(UserTarget.LOSS)
                .build();
        userResponseDto = UserResponseDto.builder()
                .id(UUID.fromString("2df05112-e154-4b69-be18-7b762b3c94c8"))
                .name("Иван")
                .email("Ivan@mail.ru")
                .age(25)
                .weight(80)
                .height(180)
                .target("Похудение")
                .dailyTarget(1500)
                .build();
    }

    @Test
    void toUserEntityTest() {
        UserEntity mappedUserEntity = userMapper.toUserEntity(userRequestDto);
        assertTrue(new ReflectionEquals(userEntity).matches(mappedUserEntity));
    }

    @Test
    void toUserResponseTest() {
        userEntity.setId(UUID.fromString("2df05112-e154-4b69-be18-7b762b3c94c8"));
        userEntity.setDailyTarget(1500);
        UserResponseDto mappedUserResponse = userMapper.toUserResponse(userEntity);
        assertTrue(new ReflectionEquals(userResponseDto).matches(mappedUserResponse));
    }
}
