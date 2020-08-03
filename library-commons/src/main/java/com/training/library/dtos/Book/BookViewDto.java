package com.training.library.dtos.Book;

import com.training.library.dtos.Details.DetailsDto;
import com.training.library.enums.CurrencyEnum;
import com.training.library.enums.LanguageEnum;
import com.training.library.enums.StateEnum;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@SuperBuilder
public class BookViewDto {

    @NonNull
    private Integer id;
    @NonNull
    private String authorName;
    @NonNull
    private String title;
    @NonNull
    private String editorial;
    @NonNull
    private Integer year;
    @NonNull
    private Integer pages;
    @NonNull
    private LanguageEnum language;
    @NonNull
    private String format;
    @NonNull
    private StateEnum state;
    @NonNull
    private Integer price;
    @NonNull
    private CurrencyEnum currency;

    private DetailsDto details;
}
