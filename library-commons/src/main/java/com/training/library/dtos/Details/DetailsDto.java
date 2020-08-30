package com.training.library.dtos.Details;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.*;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = MathDetailsDto.class, name = "Mathematics"),
        @JsonSubTypes.Type(value = HistoryDetailsDto.class, name = "History"),
        @JsonSubTypes.Type(value = PhysicsDetailsDto.class, name = "Physics"),

})
public interface DetailsDto {
}
