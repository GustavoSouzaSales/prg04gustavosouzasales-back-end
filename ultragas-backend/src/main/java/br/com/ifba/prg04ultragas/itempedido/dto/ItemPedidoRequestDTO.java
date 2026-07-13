package br.com.ifba.prg04ultragas.itempedido.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemPedidoRequestDTO {

    // Quantidade do produto no pedido
    @NotNull(message = "A quantidade é obrigatória")
    @Min(value = 1, message = "A quantidade deve ser no mínimo 1")
    private Integer quantidade;

    // Pedido ao qual o item pertence
    @NotNull(message = "O pedido é obrigatório")
    private Long pedidoId;

    // Produto adicionado ao pedido
    @NotNull(message = "O produto é obrigatório")
    private Long produtoId;
}