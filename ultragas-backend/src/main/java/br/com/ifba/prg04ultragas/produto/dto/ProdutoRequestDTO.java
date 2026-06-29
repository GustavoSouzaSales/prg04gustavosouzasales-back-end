package br.com.ifba.prg04ultragas.produto.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoRequestDTO {

    @NotBlank(message = "O nome não pode ser vazio")
    private String nome;

    @NotBlank(message = "O peso não pode ser vazio")
    private String peso;

    @NotBlank(message = "A cor não pode ser vazia")
    private String cor;

    @NotNull(message = "O preço não pode ser vazio")
    @Positive(message = "O preço deve ser maior que zero")
    private Double preco;

    @NotNull(message = "O estoque não pode ser vazio")
    @Min(value = 0, message = "O estoque não pode ser negativo")
    private Integer estoque;

    private Boolean ativo;
}