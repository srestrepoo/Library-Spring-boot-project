package com.training.library.dtos.Book;


import com.training.library.enums.LanguageEnum;
import com.training.library.enums.StateEnum;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class FilterBookDto {

    private String bookName;
    private String authorName;
    private LanguageEnum language;
    private Integer year;
    private String editorial;
    private String format;
    private StateEnum state;

}
