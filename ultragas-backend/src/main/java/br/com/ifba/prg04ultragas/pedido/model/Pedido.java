package br.com.ifba.prg04ultragas.pedido.model;

import br.com.ifba.prg04ultragas.endereco.model.Endereco;
import br.com.ifba.prg04ultragas.usuario.model.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigo;

    private LocalDateTime dataPedido;

    private String status;

    private Double valorTotal;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

    private String formaPagamento;

    private String formaRecebimento;

    private String nomeContato;

    private String telefoneContato;
}