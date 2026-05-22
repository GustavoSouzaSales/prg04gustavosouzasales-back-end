package br.com.ifba.prg04ultragas.infrastructure.exception;

// Exceção personalizada da aplicação
public class BusinessException extends RuntimeException {

    // Recebe a mensagem do erro
    public BusinessException(String mensagem) {

        super(mensagem);
    }
}