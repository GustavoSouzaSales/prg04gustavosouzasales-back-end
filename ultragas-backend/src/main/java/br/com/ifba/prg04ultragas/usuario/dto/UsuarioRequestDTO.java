package br.com.ifba.prg04ultragas.usuario.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioRequestDTO {

    // Nome recebido na requisição
    @NotBlank(message = "O nome não pode ser vazio")
    private String nome;

    // Email recebido na requisição
    @NotBlank(message = "O email não pode ser vazio")
    @Email(message = "Email inválido")
    private String email;
}