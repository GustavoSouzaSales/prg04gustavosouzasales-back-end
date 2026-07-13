package br.com.ifba.prg04ultragas.produto.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoRequestDTO {

    // Nome obrigatório
    @NotBlank(message = "O nome não pode ser vazio")
    private String nome;

    // Peso obrigatório
    @NotBlank(message = "O peso não pode ser vazio")
    private String peso;

    // Cor obrigatória
    @NotBlank(message = "A cor não pode ser vazia")
    private String cor;

    // Preço deve ser maior que zero
    @NotNull(message = "O preço não pode ser vazio")
    @Positive(message = "O preço deve ser maior que zero")
    private Double preco;

    // Estoque não pode ser negativo
    @NotNull(message = "O estoque não pode ser vazio")
    @Min(value = 0, message = "O estoque não pode ser negativo")
    private Integer estoque;

    // Indica se o produto está ativo
    private Boolean ativo;
}