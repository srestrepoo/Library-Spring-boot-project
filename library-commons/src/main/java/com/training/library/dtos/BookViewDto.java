package com.training.library.dtos;

import com.training.library.enums.Currency;
import com.training.library.enums.Language;
import com.training.library.enums.State;
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
    private Language language;
    @NonNull
    private String format;
    @NonNull
    private State state;
    @NonNull
    private Integer price;
    @NonNull
    private Currency currency;

    private DetailsDto details;
}
