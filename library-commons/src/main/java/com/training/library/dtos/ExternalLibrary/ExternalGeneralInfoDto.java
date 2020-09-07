package com.training.library.dtos.ExternalLibrary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder=true)
public class ExternalGeneralInfoDto {

    private String editorial;
    private String formato;
    private String autor;
    List<ExternalBookDto> libros;
}
