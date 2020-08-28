package com.training.library.dtos.ExternalLibrary;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder=true)
public class ExternalBookDto {

    public String isbn;
    public String titulo;
    public Double valor;
    public Integer ano;
    public Integer paginas;

}
