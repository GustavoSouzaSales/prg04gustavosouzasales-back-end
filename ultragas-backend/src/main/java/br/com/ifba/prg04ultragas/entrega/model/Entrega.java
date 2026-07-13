package br.com.ifba.prg04ultragas.entrega.model;

import br.com.ifba.prg04ultragas.infrastructure.model.PersistenceEntity;
import br.com.ifba.prg04ultragas.pedido.model.Pedido;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Entrega extends PersistenceEntity {

    // Define se será entrega ou retirada
    private String formaRecebimento;

    // Horário preferido para receber
    private String horarioPreferido;

    private Double taxaEntrega;

    // Situação da entrega
    private String statusEntrega;

    // Cada entrega pertence a um único pedido
    @OneToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;
}