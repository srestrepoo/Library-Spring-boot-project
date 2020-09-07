package com.training.library.enums;

import lombok.Getter;

@Getter
public enum ExternalPropertyEnum {
    USERNAME("external.username"),
    PASSWORD("external.password"),
    BASE_URL("external.baseUrl");

    private String value;

    ExternalPropertyEnum(String value) {
        this.value = value;
    }
}
