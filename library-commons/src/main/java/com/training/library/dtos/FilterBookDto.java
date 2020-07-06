package com.training.library.dtos;


import com.training.library.enums.Language;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FilterBookDto {

    private String bookName;
    private String authorName;
    private Language language;
    private Integer year;
    private String editorial;
    private String format;

}
