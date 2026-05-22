package br.com.ifba.prg04ultragas.usuario.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioResponseDTO {

    // ID retornado na resposta
    private Long id;

    // Nome retornado na resposta
    private String nome;

    // Email retornado na resposta
    private String email;
}