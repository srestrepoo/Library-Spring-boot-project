package com.training.library.dtos;

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
    private String nationality;
    private String nativeLanguage;
    private Integer booksNumber;
    private Integer nativeLanguageBooks;
    private Integer foreignLanguageBooks;

}
