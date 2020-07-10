package com.training.library.dtos;

import com.training.library.enums.Currency;
import com.training.library.enums.Language;
import com.training.library.enums.State;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookViewDto {

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

}
