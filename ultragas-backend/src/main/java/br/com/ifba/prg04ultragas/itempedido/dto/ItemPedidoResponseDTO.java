package br.com.ifba.prg04ultragas.itempedido.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemPedidoResponseDTO {

    private Long id;

    private Integer quantidade;

    private Double valorUnitario;

    private Double subtotal;

    private Long pedidoId;

    private Long produtoId;

    private String nomeProduto;

    private String corProduto;

    private String pesoProduto;
}