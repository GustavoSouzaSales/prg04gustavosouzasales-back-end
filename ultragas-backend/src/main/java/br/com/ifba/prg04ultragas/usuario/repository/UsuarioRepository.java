package br.com.ifba.prg04ultragas.usuario.repository;

import br.com.ifba.prg04ultragas.usuario.model.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;

// Repository responsável por acessar o banco de dados
public interface UsuarioRepository
        extends JpaRepository<Usuario, Long> {

}