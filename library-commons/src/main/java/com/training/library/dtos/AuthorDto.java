package com.training.library.dtos;

import com.training.library.enums.Language;
import com.training.library.enums.Nationality;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorDto {

    private Integer id;
    @NonNull
    private String name;
    @NonNull
    private Nationality nationality;
    @NonNull
    private Language nativeLanguage;

}
