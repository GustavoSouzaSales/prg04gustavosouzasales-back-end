package br.com.ifba.prg04ultragas.log.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogRequestDTO {

    @NotBlank(message = "A ação é obrigatória")
    private String acao;

    private String descricao;

    private String ip;

    @NotNull(message = "O usuário é obrigatório")
    private Long usuarioId;
}