package br.com.ifba.prg04ultragas.pedido.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PedidoResponseDTO {

    private Long id;

    private String codigo;

    private LocalDateTime dataPedido;

    private String status;

    private Double valorTotal;

    private Long usuarioId;

    private Long enderecoId;

    private String formaPagamento;

    private String formaRecebimento;

    private String nomeContato;

    private String telefoneContato;
}