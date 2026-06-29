package br.com.ifba.prg04ultragas.itempedido.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemPedidoRequestDTO {

    @NotNull(message = "A quantidade é obrigatória")
    @Min(value = 1, message = "A quantidade deve ser no mínimo 1")
    private Integer quantidade;

    @NotNull(message = "O pedido é obrigatório")
    private Long pedidoId;

    @NotNull(message = "O produto é obrigatório")
    private Long produtoId;
}