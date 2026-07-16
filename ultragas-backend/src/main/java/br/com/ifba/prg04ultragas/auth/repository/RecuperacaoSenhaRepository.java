package br.com.ifba.prg04ultragas.auth.repository;

import br.com.ifba.prg04ultragas.auth.model.RecuperacaoSenha;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// Repositório responsável pelas operações no banco
public interface RecuperacaoSenhaRepository
        extends JpaRepository<RecuperacaoSenha, Long> {

    // Procura o último código válido enviado para o e-mail
    Optional<RecuperacaoSenha>
    findTopByEmailAndCodigoAndUsadoFalseOrderByIdDesc(
            String email,
            String codigo
    );

    // Procura um token válido após a verificação do código
    Optional<RecuperacaoSenha>
    findByTokenAndVerificadoTrueAndUsadoFalse(
            String token
    );
}