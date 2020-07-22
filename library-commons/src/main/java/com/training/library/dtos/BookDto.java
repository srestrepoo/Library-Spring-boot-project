package com.training.library.dtos;

import com.training.library.enums.Currency;
import com.training.library.enums.Language;
import com.training.library.enums.State;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    private Language language;
    @NonNull
    private String format;
    @NonNull
    private String isbn;
    @NonNull
    private State state;
    @NonNull
    private Integer price;
    @NonNull
    private Currency currency;

    private Details details;

}
