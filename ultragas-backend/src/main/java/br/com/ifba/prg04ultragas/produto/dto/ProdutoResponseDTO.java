package br.com.ifba.prg04ultragas.produto.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoResponseDTO {

    private Long id;

    private String nome;

    private String peso;

    private String cor;

    private Double preco;

    private Integer estoque;

    private Boolean ativo;
}