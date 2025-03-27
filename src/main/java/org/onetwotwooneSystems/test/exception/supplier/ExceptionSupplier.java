package org.onetwotwooneSystems.test.exception.supplier;

import org.onetwotwooneSystems.test.exception.ResourceNotFoundException;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;


@Component
public class ExceptionSupplier {

    public Supplier<ResourceNotFoundException> userNotFound(String userId) {
        return () -> new ResourceNotFoundException(String.format("Пользователь с id %s не найден", userId));
    }

    public Supplier<ResourceNotFoundException> mealNotFound(String mealId) {
        return () -> new ResourceNotFoundException(String.format("Блюдо с id %s не найдено", mealId));
    }

    public Supplier<ResourceNotFoundException> mealHistoryNotFound(String mealHistoryId) {
        return () -> new ResourceNotFoundException(String.format("История приема блюда с id %s не найдена", mealHistoryId));
    }

}
