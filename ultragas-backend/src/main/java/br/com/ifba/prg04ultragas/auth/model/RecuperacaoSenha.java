package br.com.ifba.prg04ultragas.auth.model;

import br.com.ifba.prg04ultragas.infrastructure.model.PersistenceEntity;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class RecuperacaoSenha extends PersistenceEntity {

    // E-mail do usuário
    private String email;

    // Código enviado para o e-mail
    private String codigo;

    // Token utilizado apenas após a confirmação do código
    private String token;

    // Data de expiração
    private LocalDateTime dataExpiracao;

    // Código já confirmado?
    private Boolean verificado;

    // Recuperação já utilizada?
    private Boolean usado;
}