package br.com.ifba.prg04ultragas.pagamento.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagamentoResponseDTO {

    private Long id;

    private String formaPagamento;

    private String statusPagamento;

    private Double valorPago;

    private Double valorTroco;

    private Long pedidoId;
}