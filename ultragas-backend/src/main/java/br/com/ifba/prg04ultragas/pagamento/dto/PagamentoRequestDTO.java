package br.com.ifba.prg04ultragas.pagamento.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagamentoRequestDTO {

    @NotBlank(message = "A forma de pagamento é obrigatória")
    private String formaPagamento;

    private String statusPagamento;

    private Double valorPago;

    private Double valorTroco;

    @NotNull(message = "O pedido é obrigatório")
    private Long pedidoId;
}