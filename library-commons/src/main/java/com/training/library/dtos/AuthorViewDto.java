package com.training.library.dtos;

import com.training.library.enums.Language;
import com.training.library.enums.Nationality;
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
    private Nationality nationality;
    private Language nativeLanguage;
    private Integer booksNumber;
    private Integer nativeLanguageBooks;
    private Integer foreignLanguageBooks;

}
