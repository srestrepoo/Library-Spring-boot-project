package com.training.library.dtos.Author;

import com.training.library.enums.LanguageEnum;
import com.training.library.enums.NationalityEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorViewDto {

    private Integer id;
    private String name;
    private NationalityEnum nationality;
    private LanguageEnum nativeLanguage;
    private Integer booksNumber;
    private Integer nativeLanguageBooks;
    private Integer foreignLanguageBooks;

}
