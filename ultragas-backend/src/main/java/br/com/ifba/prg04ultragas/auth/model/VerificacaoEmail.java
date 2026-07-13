package br.com.ifba.prg04ultragas.auth.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VerificacaoEmail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Email que será verificado
    private String email;

    // Código enviado para o usuário
    private String codigo;

    // Tempo limite para usar o código
    private LocalDateTime dataExpiracao;

    // Indica se o código já foi utilizado
    private Boolean usado;
}