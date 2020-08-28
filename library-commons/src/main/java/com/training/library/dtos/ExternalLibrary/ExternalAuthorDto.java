package com.training.library.dtos.ExternalLibrary;

import com.training.library.enums.LanguageEnum;
import com.training.library.enums.NationalityEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder=true)
public class ExternalAuthorDto {

    private String nombre;
    private LanguageEnum idioma;
    private NationalityEnum pais;

}
