package br.com.ifba.prg04ultragas.usuario.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioRequestDTO {

    @NotBlank(message = "O nome não pode ser vazio")
    private String nome;

    @NotBlank(message = "O email não pode ser vazio")
    @Email(message = "Email inválido")
    private String email;

    private String telefone;

    @NotBlank(message = "A senha não pode ser vazia")
    private String senha;

    private String status;

    private String tipoUsuario;
}