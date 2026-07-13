package br.com.ifba.prg04ultragas.notificacao.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class NotificacaoResponseDTO {

    private Long id;

    private String titulo;

    private String mensagem;

    private String tipo;

    private Boolean lida;

    private LocalDateTime dataCriacao;

    private Long usuarioId;
}