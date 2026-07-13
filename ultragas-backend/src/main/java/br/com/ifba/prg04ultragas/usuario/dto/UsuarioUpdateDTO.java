package br.com.ifba.prg04ultragas.usuario.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioUpdateDTO {

    // Nome não pode ficar vazio
    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    // Valida se o email foi preenchido e se é válido
    @NotBlank(message = "O email é obrigatório")
    @Email(message = "Email inválido")
    private String email;

    private String telefone;

    // Senha atual para confirmar a alteração
    private String senhaAtual;

    // Nova senha que será cadastrada
    private String novaSenha;

    private String status;

    private String tipoUsuario;
}