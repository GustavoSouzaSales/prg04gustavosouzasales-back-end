package br.com.ifba.prg04ultragas.auth.service;

import br.com.ifba.prg04ultragas.auth.dto.GoogleLoginRequestDTO;
import br.com.ifba.prg04ultragas.auth.dto.LoginRequestDTO;
import br.com.ifba.prg04ultragas.auth.dto.VerificarCodigoRequestDTO;
import br.com.ifba.prg04ultragas.auth.model.VerificacaoEmail;
import br.com.ifba.prg04ultragas.auth.repository.VerificacaoEmailRepository;
import br.com.ifba.prg04ultragas.infrastructure.exception.BusinessException;
import br.com.ifba.prg04ultragas.log.service.LogService;
import br.com.ifba.prg04ultragas.usuario.dto.UsuarioRequestDTO;
import br.com.ifba.prg04ultragas.usuario.dto.UsuarioResponseDTO;
import br.com.ifba.prg04ultragas.usuario.model.Usuario;
import br.com.ifba.prg04ultragas.usuario.repository.UsuarioRepository;
import br.com.ifba.prg04ultragas.auth.dto.EsqueciSenhaRequestDTO;
import br.com.ifba.prg04ultragas.auth.dto.NovaSenhaRequestDTO;
import br.com.ifba.prg04ultragas.auth.model.RecuperacaoSenha;
import br.com.ifba.prg04ultragas.auth.repository.RecuperacaoSenhaRepository;

import java.util.UUID;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private VerificacaoEmailRepository verificacaoEmailRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private LogService logService;

    @Autowired
    private RecuperacaoSenhaRepository recuperacaoSenhaRepository;

    // Login com e-mail e senha
    public UsuarioResponseDTO login(LoginRequestDTO dto) {

        Usuario usuario = usuarioRepository.findByEmail(dto.getEmail())
                .orElseThrow(() ->
                        new BusinessException("E-mail ou senha inválidos"));

        // Verifica se a conta já foi ativada
        if (!"Ativo".equalsIgnoreCase(usuario.getStatus())) {
            throw new BusinessException(
                    "Conta ainda não verificada. Verifique seu e-mail."
            );
        }

        // Impede login por senha em contas do Google
        if (usuario.getSenha() == null || usuario.getSenha().isBlank()) {
            throw new BusinessException(
                    "Esta conta foi criada com Google. Use o botão Google para entrar."
            );
        }

        if (!usuario.getSenha().equals(dto.getSenha())) {
            throw new BusinessException("E-mail ou senha inválidos");
        }

        // Registra o login no histórico
        logService.registrarAuditoria(
                "LOGIN",
                "Usuário realizou login com e-mail e senha.",
                "Usuario",
                usuario.getId(),
                usuario
        );

        return toResponse(usuario);
    }

    @Transactional
    public UsuarioResponseDTO loginGoogle(GoogleLoginRequestDTO dto) {

        Usuario usuarioExistente =
                usuarioRepository.findByEmail(dto.getEmail()).orElse(null);

        boolean contaCriada = usuarioExistente == null;

        Usuario usuario;

        // Cria a conta caso seja o primeiro acesso
        if (contaCriada) {
            usuario = new Usuario();

            usuario.setNome(dto.getNome());
            usuario.setEmail(dto.getEmail());
            usuario.setTelefone("");
            usuario.setSenha("");
            usuario.setStatus("Ativo");
            usuario.setTipoUsuario("CLIENTE");

            usuario = usuarioRepository.save(usuario);

            logService.registrarAuditoria(
                    "CADASTRO_GOOGLE",
                    "Conta criada por meio do Google.",
                    "Usuario",
                    usuario.getId(),
                    usuario
            );
        } else {
            usuario = usuarioExistente;
        }

        logService.registrarAuditoria(
                "LOGIN_GOOGLE",
                "Usuário realizou login com Google.",
                "Usuario",
                usuario.getId(),
                usuario
        );

        return toResponse(usuario);
    }

    @Transactional
    public UsuarioResponseDTO cadastrarComVerificacao(
            UsuarioRequestDTO dto
    ) {

        // Impede cadastro de e-mails repetidos
        if (usuarioRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new BusinessException(
                    "Este e-mail já está cadastrado."
            );
        }

        Usuario usuario = new Usuario();

        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setTelefone(dto.getTelefone());
        usuario.setSenha(dto.getSenha());
        usuario.setStatus("Pendente");
        usuario.setTipoUsuario("CLIENTE");

        usuario = usuarioRepository.save(usuario);

        // Gera e salva o código de verificação
        String codigo = gerarCodigo();

        VerificacaoEmail verificacao = new VerificacaoEmail();

        verificacao.setEmail(usuario.getEmail());
        verificacao.setCodigo(codigo);
        verificacao.setDataExpiracao(
                LocalDateTime.now().plusMinutes(1)
        );
        verificacao.setUsado(false);

        verificacaoEmailRepository.save(verificacao);

        emailService.enviarCodigo(usuario.getEmail(), codigo);

        logService.registrarAuditoria(
                "CADASTRO_USUARIO",
                "Usuário realizou cadastro e aguarda verificação do e-mail.",
                "Usuario",
                usuario.getId(),
                usuario
        );

        return toResponse(usuario);
    }

    @Transactional
    public UsuarioResponseDTO verificarCodigo(
            VerificarCodigoRequestDTO dto
    ) {

        VerificacaoEmail verificacao = verificacaoEmailRepository
                .findTopByEmailAndCodigoAndUsadoFalseOrderByIdDesc(
                        dto.getEmail(),
                        dto.getCodigo()
                )
                .orElseThrow(() ->
                        new BusinessException("Código inválido.")
                );

        // Verifica se o código expirou
        if (verificacao.getDataExpiracao()
                .isBefore(LocalDateTime.now())) {

            throw new BusinessException(
                    "Código expirado. Solicite um novo código."
            );
        }

        Usuario usuario = usuarioRepository
                .findByEmail(dto.getEmail())
                .orElseThrow(() ->
                        new BusinessException("Usuário não encontrado.")
                );

        usuario.setStatus("Ativo");
        usuario = usuarioRepository.save(usuario);

        verificacao.setUsado(true);
        verificacaoEmailRepository.save(verificacao);

        logService.registrarAuditoria(
                "VERIFICACAO_EMAIL",
                "E-mail verificado e conta ativada com sucesso.",
                "Usuario",
                usuario.getId(),
                usuario
        );

        return toResponse(usuario);
    }

    @Transactional
    public void reenviarCodigo(String email) {

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() ->
                        new BusinessException("Usuário não encontrado.")
                );

        if ("Ativo".equalsIgnoreCase(usuario.getStatus())) {
            throw new BusinessException(
                    "Esta conta já está verificada."
            );
        }

        // Gera um novo código
        String codigo = gerarCodigo();

        VerificacaoEmail verificacao = new VerificacaoEmail();

        verificacao.setEmail(email);
        verificacao.setCodigo(codigo);
        verificacao.setDataExpiracao(
                LocalDateTime.now().plusMinutes(1)
        );
        verificacao.setUsado(false);

        verificacaoEmailRepository.save(verificacao);

        emailService.enviarCodigo(email, codigo);

        logService.registrarAuditoria(
                "REENVIO_CODIGO",
                "Novo código de verificação enviado para o e-mail do usuário.",
                "Usuario",
                usuario.getId(),
                usuario
        );
    }

    // Gera um código aleatório de 6 dígitos
    private String gerarCodigo() {
        int codigo = 100000 + new Random().nextInt(900000);
        return String.valueOf(codigo);
    }

    // Converte a entidade para DTO
    private UsuarioResponseDTO toResponse(Usuario usuario) {

        UsuarioResponseDTO response =
                new UsuarioResponseDTO();

        response.setId(usuario.getId());
        response.setNome(usuario.getNome());
        response.setEmail(usuario.getEmail());
        response.setTelefone(usuario.getTelefone());
        response.setStatus(usuario.getStatus());
        response.setTipoUsuario(usuario.getTipoUsuario());

        return response;
    }

    @Transactional
    public void solicitarRecuperacaoSenha(EsqueciSenhaRequestDTO dto) {

        Usuario usuario = usuarioRepository.findByEmail(dto.getEmail())
                .orElseThrow(() ->
                        new BusinessException("Este e-mail não está cadastrado.")
                );

        // Contas do Google não possuem senha local
        if (usuario.getSenha() == null || usuario.getSenha().isBlank()) {
            throw new BusinessException(
                    "Esta conta foi criada com Google. Use o botão Google para entrar."
            );
        }

        // Gera um token único para recuperação
        String token = UUID.randomUUID().toString();

        RecuperacaoSenha recuperacao = new RecuperacaoSenha();
        recuperacao.setEmail(usuario.getEmail());
        recuperacao.setToken(token);
        recuperacao.setDataExpiracao(LocalDateTime.now().plusMinutes(15));
        recuperacao.setUsado(false);

        recuperacaoSenhaRepository.save(recuperacao);

        String link =
                "http://localhost:5173/nova-senha?token=" + token;

        emailService.enviarRecuperacaoSenha(
                usuario.getEmail(),
                link
        );

        logService.registrarAuditoria(
                "SOLICITACAO_RECUPERACAO_SENHA",
                "Usuário solicitou a recuperação da senha.",
                "Usuario",
                usuario.getId(),
                usuario
        );
    }

    @Transactional
    public void redefinirSenha(NovaSenhaRequestDTO dto) {

        RecuperacaoSenha recuperacao = recuperacaoSenhaRepository
                .findByTokenAndUsadoFalse(dto.getToken())
                .orElseThrow(() ->
                        new BusinessException("Link de recuperação inválido ou já utilizado.")
                );

        // Verifica se o link ainda é válido
        if (recuperacao.getDataExpiracao().isBefore(LocalDateTime.now())) {
            throw new BusinessException("O link de recuperação expirou.");
        }

        Usuario usuario = usuarioRepository
                .findByEmail(recuperacao.getEmail())
                .orElseThrow(() ->
                        new BusinessException("Usuário não encontrado.")
                );

        usuario.setSenha(dto.getNovaSenha());

        usuarioRepository.saveAndFlush(usuario);

        recuperacao.setUsado(true);
        recuperacaoSenhaRepository.saveAndFlush(recuperacao);

        logService.registrarAuditoria(
                "REDEFINICAO_SENHA",
                "Usuário redefiniu a senha da conta.",
                "Usuario",
                usuario.getId(),
                usuario
        );
    }
}