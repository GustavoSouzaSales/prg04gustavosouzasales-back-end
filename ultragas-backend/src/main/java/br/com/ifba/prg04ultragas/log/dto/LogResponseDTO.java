package br.com.ifba.prg04ultragas.log.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class LogResponseDTO {

    private Long id;

    private String acao;

    private String descricao;

    private String entidade;

    private Long entidadeId;

    private String usuarioNome;

    private String usuarioEmail;

    private String ip;

    private LocalDateTime dataHora;

    private Long usuarioId;
}