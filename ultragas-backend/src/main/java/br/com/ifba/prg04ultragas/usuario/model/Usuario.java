package br.com.ifba.prg04ultragas.usuario.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity // Define essa classe como entidade do banco

@Getter
@Setter

@NoArgsConstructor // Cria construtor vazio automaticamente
@AllArgsConstructor // Cria construtor com todos os atributos

public class Usuario {

    @Id // Define o ID da entidade
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Gera IDs automáticos
    private Long id;

    // Nome do usuário
    private String nome;

    // Email do usuário
    private String email;
}