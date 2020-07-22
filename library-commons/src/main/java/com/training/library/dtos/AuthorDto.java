package com.training.library.dtos;

import com.training.library.enums.LanguageEnum;
import com.training.library.enums.NationalityEnum;
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
    private NationalityEnum nationality;
    @NonNull
    private LanguageEnum nativeLanguage;

}
