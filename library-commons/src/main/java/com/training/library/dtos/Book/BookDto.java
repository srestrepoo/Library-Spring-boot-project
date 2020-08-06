package com.training.library.dtos.Book;

import com.training.library.dtos.Details.DetailsDto;
import com.training.library.enums.CurrencyEnum;
import com.training.library.enums.LanguageEnum;
import com.training.library.enums.StateEnum;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder=true)
public class BookDto {

    private Integer id;
    @NonNull
    private Integer authorId;
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
    private String isbn;
    @NonNull
    private StateEnum state;
    @NonNull
    private Integer price;
    @NonNull
    private CurrencyEnum currency;

    private Boolean active;

    private DetailsDto detailsDto;

}
