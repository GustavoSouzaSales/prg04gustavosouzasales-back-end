package br.com.ifba.prg04ultragas.pagamento.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagamentoRequestDTO {

    // Forma de pagamento escolhida
    @NotBlank(message = "A forma de pagamento é obrigatória")
    private String formaPagamento;

    // Status do pagamento
    private String statusPagamento;

    private Double valorPago;

    private Double valorTroco;

    // Pedido relacionado ao pagamento
    @NotNull(message = "O pedido é obrigatório")
    private Long pedidoId;
}