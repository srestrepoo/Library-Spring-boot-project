package com.training.library.dtos;

import com.training.library.enums.Currency;
import com.training.library.enums.Language;
import com.training.library.enums.Nationality;
import com.training.library.enums.State;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class DetailBookViewDto {

    private Integer id;
    private String authorName;
    private String title;
    private String editorial;
    private Integer year;
    private Integer pages;
    private Language language;
    private String format;
    private State state;
    private Integer price;
    private Currency currency;
    private String subcategory;
    private String exercise;
    private String answer;
    private String historicalPeriod;
    private String censure;
    private Nationality country;

}
