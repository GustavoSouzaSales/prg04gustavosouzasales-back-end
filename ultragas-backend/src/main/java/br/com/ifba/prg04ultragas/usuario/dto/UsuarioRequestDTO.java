package br.com.ifba.prg04ultragas.usuario.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioRequestDTO {

    // Nome recebido na requisição
    private String nome;

    // Email recebido na requisição
    private String email;
}