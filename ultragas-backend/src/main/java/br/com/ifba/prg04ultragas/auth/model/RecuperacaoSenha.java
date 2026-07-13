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

    // Email do usuário que solicitou a recuperação
    private String email;

    // Token único enviado por e-mail
    private String token;

    // Data limite para uso do token
    private LocalDateTime dataExpiracao;

    // Indica se o token já foi utilizado
    private Boolean usado;
}