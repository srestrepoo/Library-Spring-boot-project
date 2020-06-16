package com.training.library.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorDto {

    private Integer id;
    private String name;
    private String nationality;
    private String nativeLanguage;
    private Set<BookDto> books;

}
