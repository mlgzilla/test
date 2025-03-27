package org.onetwotwooneSystems.test.service.impl;

import lombok.RequiredArgsConstructor;
import org.onetwotwooneSystems.test.entity.UserEntity;
import org.onetwotwooneSystems.test.exception.supplier.ExceptionSupplier;
import org.onetwotwooneSystems.test.mapper.UserMapper;
import org.onetwotwooneSystems.test.model.UserRequestDto;
import org.onetwotwooneSystems.test.model.UserResponseDto;
import org.onetwotwooneSystems.test.repository.UserRepository;
import org.onetwotwooneSystems.test.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Integer WEIGHT_COEFFICIENT = 10;
    private static final Double HEIGHT_COEFFICIENT = 6.25;
    private static final Integer AGE_COEFFICIENT = 5;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ExceptionSupplier exceptionSupplier;

    @Override
    @Transactional
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        UserEntity mappedUserEntity = userMapper.toUserEntity(userRequestDto);
        Integer dailyTarget = (int) (WEIGHT_COEFFICIENT * mappedUserEntity.getWeight() +
                HEIGHT_COEFFICIENT * mappedUserEntity.getHeight() -
                AGE_COEFFICIENT * mappedUserEntity.getAge() + mappedUserEntity.getTarget().getAdditionalCalories());
        mappedUserEntity.setDailyTarget(dailyTarget);
        UserEntity savedUserEntity = userRepository.save(mappedUserEntity);
        return userMapper.toUserResponse(savedUserEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public String getDailyTargetProgress(String id) {
        Integer dailyTarget = userRepository.getDailyTargetByUserId(UUID.fromString(id))
                .orElseThrow(exceptionSupplier.userNotFound(id));
        return "Для выполнения дневной нормы осталось потребить " + dailyTarget + " каллорий";
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDto getUserById(String id) {
        UserEntity userEntity = userRepository.findById(UUID.fromString(id))
                .orElseThrow(exceptionSupplier.userNotFound(id));
        return userMapper.toUserResponse(userEntity);
    }

    @Transactional
    public void resetDailyTarget() {
        userRepository.resetDailyTargetGain();
        userRepository.resetDailyTargetLoss();
        userRepository.resetDailyTargetMaintain();
    }
}
