package br.com.ifba.prg04ultragas.auth.repository;

import br.com.ifba.prg04ultragas.auth.model.VerificacaoEmail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// Repositório responsável pelas operações no banco
public interface VerificacaoEmailRepository extends JpaRepository<VerificacaoEmail, Long> {

    Optional<VerificacaoEmail> findTopByEmailAndCodigoAndUsadoFalseOrderByIdDesc(
            String email,
            String codigo
    );
}