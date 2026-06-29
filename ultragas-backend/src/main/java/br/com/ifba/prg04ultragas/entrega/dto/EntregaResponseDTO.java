package br.com.ifba.prg04ultragas.entrega.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntregaResponseDTO {

    private Long id;

    private String formaRecebimento;

    private String horarioPreferido;

    private Double taxaEntrega;

    private String statusEntrega;

    private Long pedidoId;
}