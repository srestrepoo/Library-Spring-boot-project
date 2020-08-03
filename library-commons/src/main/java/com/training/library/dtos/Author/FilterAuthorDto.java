package com.training.library.dtos.Author;


import com.training.library.enums.LanguageEnum;
import com.training.library.enums.NationalityEnum;
import com.training.library.enums.StateEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FilterAuthorDto {

    private String name;
    private LanguageEnum nativeLanguage;
    private NationalityEnum nationality;

}
