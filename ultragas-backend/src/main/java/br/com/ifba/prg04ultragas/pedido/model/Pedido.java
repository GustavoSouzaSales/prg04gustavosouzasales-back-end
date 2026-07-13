package br.com.ifba.prg04ultragas.pedido.model;

import br.com.ifba.prg04ultragas.endereco.model.Endereco;
import br.com.ifba.prg04ultragas.infrastructure.model.PersistenceEntity;
import br.com.ifba.prg04ultragas.usuario.model.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pedido extends PersistenceEntity {

    // Código único do pedido
    private String codigo;

    // Data e hora em que o pedido foi realizado
    private LocalDateTime dataPedido;

    private String status;

    private Double valorTotal;

    // Usuário que fez o pedido
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    // Endereço de entrega
    @ManyToOne
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

    private String formaPagamento;

    private String formaRecebimento;

    private String nomeContato;

    private String telefoneContato;
}