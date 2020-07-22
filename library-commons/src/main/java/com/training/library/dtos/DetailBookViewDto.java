package com.training.library.dtos;

import com.training.library.enums.CurrencyEnum;
import com.training.library.enums.LanguageEnum;
import com.training.library.enums.NationalityEnum;
import com.training.library.enums.StateEnum;
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
    private LanguageEnum language;
    private String format;
    private StateEnum state;
    private Integer price;
    private CurrencyEnum currency;
    private String subcategory;
    private String exercise;
    private String answer;
    private String historicalPeriod;
    private String censure;
    private NationalityEnum country;

}
