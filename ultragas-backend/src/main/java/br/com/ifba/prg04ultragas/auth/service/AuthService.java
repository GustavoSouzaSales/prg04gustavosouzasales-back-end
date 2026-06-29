package br.com.ifba.prg04ultragas.auth.service;

import br.com.ifba.prg04ultragas.auth.dto.GoogleLoginRequestDTO;
import br.com.ifba.prg04ultragas.auth.dto.LoginRequestDTO;
import br.com.ifba.prg04ultragas.auth.dto.VerificarCodigoRequestDTO;
import br.com.ifba.prg04ultragas.auth.model.VerificacaoEmail;
import br.com.ifba.prg04ultragas.auth.repository.VerificacaoEmailRepository;
import br.com.ifba.prg04ultragas.infrastructure.exception.BusinessException;
import br.com.ifba.prg04ultragas.usuario.dto.UsuarioRequestDTO;
import br.com.ifba.prg04ultragas.usuario.dto.UsuarioResponseDTO;
import br.com.ifba.prg04ultragas.usuario.model.Usuario;
import br.com.ifba.prg04ultragas.usuario.repository.UsuarioRepository;

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

    public UsuarioResponseDTO login(LoginRequestDTO dto) {

        Usuario usuario = usuarioRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new BusinessException("E-mail ou senha inválidos"));

        if (!"Ativo".equalsIgnoreCase(usuario.getStatus())) {
            throw new BusinessException("Conta ainda não verificada. Verifique seu e-mail.");
        }

        if (usuario.getSenha() == null || usuario.getSenha().isBlank()) {
            throw new BusinessException("Esta conta foi criada com Google. Use o botão Google para entrar.");
        }

        if (!usuario.getSenha().equals(dto.getSenha())) {
            throw new BusinessException("E-mail ou senha inválidos");
        }

        return toResponse(usuario);
    }

    public UsuarioResponseDTO loginGoogle(GoogleLoginRequestDTO dto) {

        Usuario usuario = usuarioRepository.findByEmail(dto.getEmail())
                .orElseGet(() -> {
                    Usuario novo = new Usuario();

                    novo.setNome(dto.getNome());
                    novo.setEmail(dto.getEmail());
                    novo.setTelefone("");
                    novo.setSenha("");
                    novo.setStatus("Ativo");
                    novo.setTipoUsuario("CLIENTE");

                    return usuarioRepository.save(novo);
                });

        return toResponse(usuario);
    }

    @Transactional
    public UsuarioResponseDTO cadastrarComVerificacao(UsuarioRequestDTO dto) {

        if (usuarioRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new BusinessException("Este e-mail já está cadastrado.");
        }



        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setTelefone(dto.getTelefone());
        usuario.setSenha(dto.getSenha());
        usuario.setStatus("Pendente");
        usuario.setTipoUsuario("CLIENTE");

        usuario = usuarioRepository.save(usuario);

        String codigo = gerarCodigo();

        VerificacaoEmail verificacao = new VerificacaoEmail();
        verificacao.setEmail(usuario.getEmail());
        verificacao.setCodigo(codigo);
        verificacao.setDataExpiracao(LocalDateTime.now().plusMinutes(1));
        verificacao.setUsado(false);

        verificacaoEmailRepository.save(verificacao);

        emailService.enviarCodigo(usuario.getEmail(), codigo);

        return toResponse(usuario);
    }

    @Transactional
    public UsuarioResponseDTO verificarCodigo(VerificarCodigoRequestDTO dto) {

        VerificacaoEmail verificacao = verificacaoEmailRepository
                .findTopByEmailAndCodigoAndUsadoFalseOrderByIdDesc(
                        dto.getEmail(),
                        dto.getCodigo()
                )
                .orElseThrow(() -> new BusinessException("Código inválido."));

        if (verificacao.getDataExpiracao().isBefore(LocalDateTime.now())) {
            throw new BusinessException("Código expirado. Solicite um novo código.");
        }

        Usuario usuario = usuarioRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new BusinessException("Usuário não encontrado."));

        usuario.setStatus("Ativo");
        usuario = usuarioRepository.save(usuario);

        verificacao.setUsado(true);
        verificacaoEmailRepository.save(verificacao);

        return toResponse(usuario);
    }

    private String gerarCodigo() {
        int codigo = 100000 + new Random().nextInt(900000);
        return String.valueOf(codigo);
    }

    private UsuarioResponseDTO toResponse(Usuario usuario) {
        UsuarioResponseDTO response = new UsuarioResponseDTO();

        response.setId(usuario.getId());
        response.setNome(usuario.getNome());
        response.setEmail(usuario.getEmail());
        response.setTelefone(usuario.getTelefone());
        response.setStatus(usuario.getStatus());
        response.setTipoUsuario(usuario.getTipoUsuario());

        return response;
    }

    @Transactional
    public void reenviarCodigo(String email) {

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException("Usuário não encontrado."));

        if ("Ativo".equalsIgnoreCase(usuario.getStatus())) {
            throw new BusinessException("Esta conta já está verificada.");
        }

        String codigo = gerarCodigo();

        VerificacaoEmail verificacao = new VerificacaoEmail();
        verificacao.setEmail(email);
        verificacao.setCodigo(codigo);
        verificacao.setDataExpiracao(LocalDateTime.now().plusMinutes(1));
        verificacao.setUsado(false);

        verificacaoEmailRepository.save(verificacao);

        emailService.enviarCodigo(email, codigo);
    }
}