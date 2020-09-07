package com.training.library.dtos.Book;

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
    private Integer authorId;
    private String authorName;
    private String title;
    private String editorial;
    private Integer year;
    private Integer pages;
    private LanguageEnum language;
    private String format;
    private String isbn;
    private StateEnum state;
    private Double price;
    private CurrencyEnum currency;

    private String subcategory;
    private String exercise;
    private String answer;
    private String historicalPeriod;
    private String censure;
    private NationalityEnum country;

}
