package com.training.library.dtos.ExternalLibrary;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder()
public class ExternalAuthorFilterDto {
    private String nombre;
}
