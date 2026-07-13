package br.com.ifba.prg04ultragas.auth.repository;

import br.com.ifba.prg04ultragas.auth.model.RecuperacaoSenha;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// Repositório responsável pelas operações no banco
public interface RecuperacaoSenhaRepository
        extends JpaRepository<RecuperacaoSenha, Long> {

    Optional<RecuperacaoSenha> findByTokenAndUsadoFalse(String token);
}