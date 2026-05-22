package br.com.ifba.prg04ultragas.infrastructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// Classe responsável por tratar erros da aplicação
@RestControllerAdvice
public class ApiExceptionHandler {

    // Captura erros do tipo BusinessException
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<String> handleBusinessException(
            BusinessException ex
    ) {

        // Retorna erro 400 + mensagem personalizada
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }
}