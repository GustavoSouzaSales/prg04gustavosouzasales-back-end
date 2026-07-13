package br.com.ifba.prg04ultragas.usuario.model;

import br.com.ifba.prg04ultragas.infrastructure.model.PersistenceEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario extends PersistenceEntity {


    private String nome;

    @Column(unique = true)
    private String email;

    private String telefone;

    private String senha;

    private String status; // Ativo, Pendente, Inativo

    private String tipoUsuario; // CLIENTE ou ADMINISTRADOR
}