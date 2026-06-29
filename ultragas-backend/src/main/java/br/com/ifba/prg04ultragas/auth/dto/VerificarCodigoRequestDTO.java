package br.com.ifba.prg04ultragas.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerificarCodigoRequestDTO {

    private String email;

    private String codigo;
}