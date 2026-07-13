package br.com.ifba.prg04ultragas.pagamento.model;

import br.com.ifba.prg04ultragas.infrastructure.model.PersistenceEntity;
import br.com.ifba.prg04ultragas.pedido.model.Pedido;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pagamento extends PersistenceEntity {

    // Forma utilizada para pagar
    private String formaPagamento;

    // Situação do pagamento
    private String statusPagamento;

    private Double valorPago;

    private Double valorTroco;

    // Cada pagamento pertence a um único pedido
    @OneToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;
}