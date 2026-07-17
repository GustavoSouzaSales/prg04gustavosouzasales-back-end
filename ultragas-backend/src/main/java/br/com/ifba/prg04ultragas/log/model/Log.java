package br.com.ifba.prg04ultragas.log.model;

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
public class Log extends PersistenceEntity {

    // Ação realizada no sistema
    private String acao;

    // Descrição da ação
    private String descricao;

    // Entidade afetada pela ação
    private String entidade;

    private Long entidadeId;

    // Email e o nome do usuário que realizou a ação
    private String usuarioNome;
    private String usuarioEmail;

    private String ip;

    // Data e hora do registro
    private LocalDateTime dataHora;

    // Usuário responsável pela ação
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = true)
    private Usuario usuario;
}