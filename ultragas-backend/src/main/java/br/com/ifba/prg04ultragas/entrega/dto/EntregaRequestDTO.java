package br.com.ifba.prg04ultragas.entrega.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntregaRequestDTO {

    @NotBlank(message = "A forma de recebimento é obrigatória")
    private String formaRecebimento;

    private String horarioPreferido;

    private Double taxaEntrega;

    private String statusEntrega;

    @NotNull(message = "O pedido é obrigatório")
    private Long pedidoId;
}