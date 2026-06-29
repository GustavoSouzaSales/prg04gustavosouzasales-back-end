package br.com.ifba.prg04ultragas.usuario.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(unique = true)
    private String email;

    private String telefone;

    private String senha;

    private String status; // Ativo, Pendente, Inativo

    private String tipoUsuario; // CLIENTE ou ADMINISTRADOR
}