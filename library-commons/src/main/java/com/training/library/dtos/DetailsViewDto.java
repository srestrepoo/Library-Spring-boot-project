package com.training.library.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.training.library.enums.Nationality;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DetailsViewDto {

    private String subcategory;
    private String exercise;
    private String answer;
    private String historicalPeriod;
    private String censure;
    private Nationality country;

}
