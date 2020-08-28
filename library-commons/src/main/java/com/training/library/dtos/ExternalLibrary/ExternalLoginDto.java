package com.training.library.dtos.ExternalLibrary;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExternalLoginDto {

    private String nombreUsuario;
    private String contrasena;

}
