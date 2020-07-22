package com.training.library.dtos;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.*;
import com.training.library.enums.BookCategoryEnum;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = MathDetailsDto.class, name = "Mathematics"),
        @JsonSubTypes.Type(value = HistoryDetailsDto.class, name = "History"),
        @JsonSubTypes.Type(value = PhysicsDetailsDto.class, name = "Physics")
})
public interface DetailsDto {
}
