package br.com.ifba.prg04ultragas.pedido.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoRequestDTO {

    // Status do pedido
    private String status;

    // Valor total da compra
    private Double valorTotal;

    // Usuário responsável pelo pedido
    @NotNull(message = "O usuário é obrigatório")
    private Long usuarioId;

    private Long enderecoId;

    // Forma de pagamento escolhida
    private String formaPagamento;

    // Define se será entrega ou retirada
    private String formaRecebimento;

    private String nomeContato;

    private String telefoneContato;
}