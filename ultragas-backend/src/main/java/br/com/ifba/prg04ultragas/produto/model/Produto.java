package br.com.ifba.prg04ultragas.produto.model;

import br.com.ifba.prg04ultragas.infrastructure.model.PersistenceEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Produto extends PersistenceEntity {


    private String nome;

    private String peso;

    private String cor;

    private Double preco;

    private Integer estoque;

    private Boolean ativo;
}