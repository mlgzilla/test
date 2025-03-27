package org.onetwotwooneSystems.test.enums;

public enum UserTarget {
    LOSS("Похудение", -250),
    MAINTAIN("Поддержание", 0),
    GAIN("Набор массы", 250);

    private final String value;
    private final Integer additionalCalories;

    UserTarget(String value, Integer additionalCalories) {
        this.value = value;
        this.additionalCalories = additionalCalories;
    }

    public String toString() {
        return this.value;
    }

    public Integer getAdditionalCalories() {
        return this.additionalCalories;
    }
}
