package br.com.ifba.prg04ultragas.pedido.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoRequestDTO {

    private String status;

    private Double valorTotal;

    @NotNull(message = "O usuário é obrigatório")
    private Long usuarioId;

    private Long enderecoId;

    private String formaPagamento;

    private String formaRecebimento;

    private String nomeContato;

    private String telefoneContato;
}