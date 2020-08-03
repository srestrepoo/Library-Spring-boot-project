package com.training.library.enums;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

@Getter
public enum StateEnum {
    EXCELLENT(5),
    GOOD(4),
    ACCEPTABLE(3),
    BAD(2),
    DISCARDED(1);

    private final Integer value;

    StateEnum(Integer value){
        this.value = value;
    }

    public static StateEnum getStateByValue(Integer value){
        return Arrays.stream(StateEnum.values())
                .filter(state -> state.getValue().equals(value))
                .findFirst().orElse(null);
    }
}
