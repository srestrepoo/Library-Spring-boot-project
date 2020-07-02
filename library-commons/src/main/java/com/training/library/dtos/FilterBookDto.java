package com.training.library.dtos;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FilterBookDto {

    private String bookName;
    private String authorName;
    private String language;
    private Integer year;
    private String editorial;
    private String format;

}
