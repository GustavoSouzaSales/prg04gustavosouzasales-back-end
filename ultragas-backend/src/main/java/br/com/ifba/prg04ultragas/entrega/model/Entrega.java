package br.com.ifba.prg04ultragas.entrega.model;

import br.com.ifba.prg04ultragas.pedido.model.Pedido;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Entrega {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String formaRecebimento; // Entrega ou Retirada

    private String horarioPreferido; // Manhã, Tarde, Noite

    private Double taxaEntrega;

    private String statusEntrega;

    @OneToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;
}