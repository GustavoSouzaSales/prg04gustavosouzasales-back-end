package br.com.ifba.prg04ultragas.notificacao.model;

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
public class Notificacao extends PersistenceEntity {


    // Título da notificação
    private String titulo;

    // Mensagem exibida ao usuário
    @Column(length = 500)
    private String mensagem;

    // Tipo da notificação
    private String tipo;

    // Indica se já foi lida
    private Boolean lida;

    // Data de criação da notificação
    private LocalDateTime dataCriacao;

    // Usuário que receberá a notificação
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}