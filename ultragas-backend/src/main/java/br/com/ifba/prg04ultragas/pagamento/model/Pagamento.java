package br.com.ifba.prg04ultragas.pagamento.model;

import br.com.ifba.prg04ultragas.pedido.model.Pedido;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String formaPagamento;

    private String statusPagamento;

    private Double valorPago;

    private Double valorTroco;

    @OneToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;
}