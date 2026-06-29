package br.com.ifba.prg04ultragas.endereco.model;

import br.com.ifba.prg04ultragas.usuario.model.Usuario;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String cep;
    private String endereco;
    private String numero;
    private String bairro;
    private String cidade;
    private String uf;
    private Boolean principal;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}