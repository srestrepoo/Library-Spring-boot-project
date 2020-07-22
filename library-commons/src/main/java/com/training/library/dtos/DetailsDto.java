package com.training.library.dtos;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.*;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = MathDetailsDto.class, name = "MathDetails"),
        @JsonSubTypes.Type(value = HistoryDetailsDto.class, name = "HistoryDetails"),
        @JsonSubTypes.Type(value = PhysicsDetailsDto.class, name = "PhysicsDetails")
})
public interface DetailsDto {
}
