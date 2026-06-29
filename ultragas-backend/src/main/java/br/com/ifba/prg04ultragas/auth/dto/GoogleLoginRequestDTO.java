package br.com.ifba.prg04ultragas.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoogleLoginRequestDTO {
    private String nome;
    private String email;
}