package br.com.ifba.prg04ultragas.produto.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String peso;

    private String cor;

    private Double preco;

    private Integer estoque;

    private Boolean ativo;
}