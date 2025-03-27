package org.onetwotwooneSystems.test.service;

import org.onetwotwooneSystems.test.model.UserRequestDto;
import org.onetwotwooneSystems.test.model.UserResponseDto;

public interface UserService {
    UserResponseDto createUser(UserRequestDto userRequestDto);

    String getDailyTargetProgress(String id);

    UserResponseDto getUserById(String id);
}
