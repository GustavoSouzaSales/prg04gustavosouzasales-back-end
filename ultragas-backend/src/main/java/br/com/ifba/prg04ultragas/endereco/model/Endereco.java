package br.com.ifba.prg04ultragas.endereco.model;

import br.com.ifba.prg04ultragas.infrastructure.model.PersistenceEntity;
import br.com.ifba.prg04ultragas.usuario.model.Usuario;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Endereco extends PersistenceEntity {

    // Título para identificar o endereço
    private String titulo;

    private String cep;

    private String endereco;

    private String numero;

    private String bairro;

    private String cidade;

    private String uf;

    // Define se é o endereço principal
    private Boolean principal;

    // Usuário dono do endereço
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}