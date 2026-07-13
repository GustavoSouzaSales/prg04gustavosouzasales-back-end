package br.com.ifba.prg04ultragas.usuario.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioResponseDTO {

    // ID do usuário
    private Long id;

    private String nome;

    private String email;

    private String telefone;

    private String status;

    // Tipo do usuário (cliente ou administrador)
    private String tipoUsuario;
}