package br.com.ifba.prg04ultragas.auth.controller;

import br.com.ifba.prg04ultragas.auth.dto.EsqueciSenhaRequestDTO;
import br.com.ifba.prg04ultragas.auth.dto.GoogleLoginRequestDTO;
import br.com.ifba.prg04ultragas.auth.dto.LoginRequestDTO;
import br.com.ifba.prg04ultragas.auth.dto.NovaSenhaRequestDTO;
import br.com.ifba.prg04ultragas.auth.dto.TokenRecuperacaoResponseDTO;
import br.com.ifba.prg04ultragas.auth.dto.VerificarCodigoRequestDTO;
import br.com.ifba.prg04ultragas.auth.dto.VerificarRecuperacaoRequestDTO;
import br.com.ifba.prg04ultragas.auth.service.AuthService;
import br.com.ifba.prg04ultragas.usuario.dto.UsuarioRequestDTO;
import br.com.ifba.prg04ultragas.usuario.dto.UsuarioResponseDTO;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {

    @Autowired
    private AuthService service;

    @PostMapping("/login")
    public UsuarioResponseDTO login(
            @RequestBody LoginRequestDTO dto
    ) {
        return service.login(dto);
    }

    @PostMapping("/google")
    public UsuarioResponseDTO loginGoogle(
            @RequestBody GoogleLoginRequestDTO dto
    ) {
        return service.loginGoogle(dto);
    }

    @PostMapping("/cadastrar")
    public UsuarioResponseDTO cadastrar(
            @RequestBody UsuarioRequestDTO dto
    ) {
        return service.cadastrarComVerificacao(dto);
    }

    @PostMapping("/verificar-codigo")
    public UsuarioResponseDTO verificarCodigo(
            @RequestBody VerificarCodigoRequestDTO dto
    ) {
        return service.verificarCodigo(dto);
    }

    @PostMapping("/reenviar-codigo")
    public java.util.Map<String, String> reenviarCodigo(
            @RequestBody VerificarCodigoRequestDTO dto
    ) {
        service.reenviarCodigo(dto.getEmail());

        return java.util.Map.of(
                "mensagem",
                "Código reenviado com sucesso."
        );
    }

    @PostMapping("/esqueci-senha")
    public void solicitarRecuperacaoSenha(
            @RequestBody @Valid EsqueciSenhaRequestDTO dto
    ) {
        service.solicitarRecuperacaoSenha(dto);
    }

    @PostMapping("/verificar-codigo-recuperacao")
    public TokenRecuperacaoResponseDTO verificarCodigoRecuperacao(
            @RequestBody @Valid VerificarRecuperacaoRequestDTO dto
    ) {
        return service.verificarCodigoRecuperacao(dto);
    }

    @PostMapping("/nova-senha")
    public void redefinirSenha(
            @RequestBody @Valid NovaSenhaRequestDTO dto
    ) {
        service.redefinirSenha(dto);
    }
}