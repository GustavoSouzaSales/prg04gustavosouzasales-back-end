package br.com.ifba.prg04ultragas.log.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogRequestDTO {

    // Ação realizada no sistema
    @NotBlank(message = "A ação é obrigatória")
    private String acao;

    private String descricao;

    private String entidade;

    private Long entidadeId;

    private String ip;

    // Usuário responsável pelo log
    @NotNull(message = "O usuário é obrigatório")
    private Long usuarioId;
}