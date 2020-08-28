package com.training.library.dtos.ExternalLibrary;

import lombok.Data;

@Data
public class ExternalCredentialsDto {

    private String token;
    private String nombreUsuario;
    private String rol;

}
