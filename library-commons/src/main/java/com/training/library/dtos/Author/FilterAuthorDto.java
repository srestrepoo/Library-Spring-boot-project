package com.training.library.dtos.Author;


import com.training.library.enums.LanguageEnum;
import com.training.library.enums.NationalityEnum;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Data
@Builder
public class FilterAuthorDto {

    private String name;
    private LanguageEnum nativeLanguage;
    private NationalityEnum nationality;
    private Integer maxResults;

}
