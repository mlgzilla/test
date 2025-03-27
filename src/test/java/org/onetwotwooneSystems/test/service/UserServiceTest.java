package org.onetwotwooneSystems.test.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.onetwotwooneSystems.test.TestApplicationTests;
import org.onetwotwooneSystems.test.entity.UserEntity;
import org.onetwotwooneSystems.test.enums.UserTarget;
import org.onetwotwooneSystems.test.mapper.UserMapper;
import org.onetwotwooneSystems.test.model.UserRequestDto;
import org.onetwotwooneSystems.test.model.UserResponseDto;
import org.onetwotwooneSystems.test.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.when;

public class UserServiceTest extends TestApplicationTests {

    private static final String dailyTargetProgress = "Для выполнения дневной нормы осталось потребить 1550 каллорий";

    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;
    @MockitoBean
    private UserRepository userRepository;

    private UserRequestDto userRequestDto;
    private UserResponseDto userResponseDto;
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
                .dailyTarget(1550)
                .build();
        userRequestDto = UserRequestDto.builder()
                .name("Иван")
                .email("Ivan@mail.ru")
                .age(25)
                .weight(80)
                .height(180)
                .target("LOSS")
                .build();
        userResponseDto = UserResponseDto.builder()
                .id(UUID.fromString("2df05112-e154-4b69-be18-7b762b3c94c8"))
                .name("Иван")
                .email("Ivan@mail.ru")
                .age(25)
                .weight(80)
                .height(180)
                .target("Похудение")
                .dailyTarget(1550)
                .build();
    }

    @Test
    void createUserTest() {
        when(userRepository.save(notNull())).thenReturn(userEntity);
        UserResponseDto mappedUserEntity = userService.createUser(userRequestDto);
        assertTrue(new ReflectionEquals(userResponseDto).matches(mappedUserEntity));
    }

    @Test
    void getDailyTargetProgressTest() {
        when(userRepository.getDailyTargetByUserId(UUID.fromString("2df05112-e154-4b69-be18-7b762b3c94c8")))
                .thenReturn(Optional.of(1550));
        String targetProgress = userService.getDailyTargetProgress("2df05112-e154-4b69-be18-7b762b3c94c8");
        assertTrue(new ReflectionEquals(dailyTargetProgress).matches(targetProgress));
    }

    @Test
    void getUserByIdTest() {
        when(userRepository.findById(UUID.fromString("2df05112-e154-4b69-be18-7b762b3c94c8")))
                .thenReturn(Optional.ofNullable(userEntity));
        UserResponseDto user = userService.getUserById("2df05112-e154-4b69-be18-7b762b3c94c8");
        assertTrue(new ReflectionEquals(userResponseDto).matches(user));
    }
}
