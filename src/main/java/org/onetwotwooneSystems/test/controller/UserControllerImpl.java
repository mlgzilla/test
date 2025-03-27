package org.onetwotwooneSystems.test.controller;

import lombok.RequiredArgsConstructor;
import org.onetwotwooneSystems.test.UserApi;
import org.onetwotwooneSystems.test.model.UserRequestDto;
import org.onetwotwooneSystems.test.model.UserResponseDto;
import org.onetwotwooneSystems.test.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserApi {
    private final UserService userService;

    @Override
    public ResponseEntity<UserResponseDto> createUser(UserRequestDto userRequestDto) {
        UserResponseDto userResponse = userService.createUser(userRequestDto);
        return ResponseEntity.ok(userResponse);
    }

    @Override
    public ResponseEntity<String> getDailyTargetProgress(String id) {
        String progress = userService.getDailyTargetProgress(id);
        return ResponseEntity.ok(progress);
    }

    @Override
    public ResponseEntity<UserResponseDto> getUserById(String id) {
        UserResponseDto user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }
}
