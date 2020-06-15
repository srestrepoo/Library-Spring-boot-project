package com.training.library.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDto {

    private Integer id;
    private AuthorDto author;
    private String title;
    private String editorial;
    private Integer year;
    private Integer pages;
    private String language;
    private String format;

}
