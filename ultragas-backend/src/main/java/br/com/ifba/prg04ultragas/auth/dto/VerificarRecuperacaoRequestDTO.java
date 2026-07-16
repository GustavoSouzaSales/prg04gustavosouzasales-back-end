package br.com.ifba.prg04ultragas.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerificarRecuperacaoRequestDTO {

    @NotBlank(message = "O e-mail é obrigatório")
    private String email;

    @NotBlank(message = "O código é obrigatório")
    @Pattern(
            regexp = "\\d{6}",
            message = "O código deve possuir 6 dígitos"
    )
    private String codigo;
}