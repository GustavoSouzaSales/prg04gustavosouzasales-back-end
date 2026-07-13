package br.com.ifba.prg04ultragas.itempedido.model;

import br.com.ifba.prg04ultragas.infrastructure.model.PersistenceEntity;
import br.com.ifba.prg04ultragas.pedido.model.Pedido;
import br.com.ifba.prg04ultragas.produto.model.Produto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemPedido extends PersistenceEntity {

    // Quantidade do produto no pedido
    private Integer quantidade;

    // Preço do produto no momento da compra
    private Double valorUnitario;

    // Valor total do item (quantidade x valor unitário)
    private Double subtotal;

    // Pedido ao qual o item pertence
    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    // Produto adicionado ao pedido
    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;
}