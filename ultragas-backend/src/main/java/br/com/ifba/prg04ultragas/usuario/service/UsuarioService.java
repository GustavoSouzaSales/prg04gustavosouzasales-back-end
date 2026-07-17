package br.com.ifba.prg04ultragas.usuario.service;

import br.com.ifba.prg04ultragas.infrastructure.exception.BusinessException;
import br.com.ifba.prg04ultragas.infrastructure.mapper.ObjectMapperUtil;
import br.com.ifba.prg04ultragas.usuario.dto.UsuarioRequestDTO;
import br.com.ifba.prg04ultragas.usuario.dto.UsuarioResponseDTO;
import br.com.ifba.prg04ultragas.usuario.dto.UsuarioUpdateDTO;
import br.com.ifba.prg04ultragas.usuario.model.Usuario;
import br.com.ifba.prg04ultragas.usuario.repository.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import br.com.ifba.prg04ultragas.log.service.LogService;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private ObjectMapperUtil mapper;

    @Autowired
    private LogService logService;

    // Lista todos os usuários de forma paginada
    public Page<UsuarioResponseDTO> listarUsuarios(Pageable pageable) {

        Page<Usuario> usuarios = repository.findAll(pageable);

        return usuarios.map(usuario ->
                mapper.map(usuario, UsuarioResponseDTO.class)
        );
    }

    // Busca um usuário pelo ID
    public UsuarioResponseDTO buscarUsuarioPorId(Long id) {

        Usuario usuario = repository.findById(id)
                .orElseThrow(() ->
                        new BusinessException("Usuário não encontrado"));

        return mapper.map(usuario, UsuarioResponseDTO.class);
    }

    @Transactional
    public UsuarioResponseDTO salvarUsuario(UsuarioRequestDTO dto) {

        // Converte o DTO para a entidade
        Usuario usuario = mapper.map(dto, Usuario.class);

        // Define valores padrão caso não sejam enviados
        if (usuario.getStatus() == null || usuario.getStatus().isBlank()) {
            usuario.setStatus("Ativo");
        }

        if (usuario.getTipoUsuario() == null || usuario.getTipoUsuario().isBlank()) {
            usuario.setTipoUsuario("CLIENTE");
        }

        usuario = repository.save(usuario);

        // Registra a ação no log
        logService.registrarAuditoria(
                "CRIACAO_USUARIO",
                "Usuário criado: " + usuario.getEmail(),
                "Usuario",
                usuario.getId(),
                usuario
        );
        return mapper.map(usuario, UsuarioResponseDTO.class);
    }

    @Transactional
    public UsuarioResponseDTO atualizarUsuario(Long id, UsuarioUpdateDTO dto) {

        Usuario usuario = repository.findById(id)
                .orElseThrow(() ->
                        new BusinessException("Usuário não encontrado"));

        // Atualiza os dados do usuário
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setTelefone(dto.getTelefone());
        usuario.setStatus(dto.getStatus());
        usuario.setTipoUsuario(dto.getTipoUsuario());

        // Só altera a senha se uma nova for informada
        if (dto.getNovaSenha() != null && !dto.getNovaSenha().isBlank()) {

            // Confere se a senha atual está correta
            if (dto.getSenhaAtual() == null || !dto.getSenhaAtual().equals(usuario.getSenha())) {
                throw new BusinessException("Senha atual inválida");
            }

            usuario.setSenha(dto.getNovaSenha());
        }

        usuario = repository.save(usuario);

        // Registra a alteração no log
        logService.registrarAuditoria(
                "ATUALIZACAO_USUARIO",
                "Usuário atualizado: " + usuario.getEmail(),
                "Usuario",
                usuario.getId(),
                usuario
        );

        return mapper.map(usuario, UsuarioResponseDTO.class);
    }

    @Transactional
    public void deletarUsuario(Long id) {

        Usuario usuario = repository.findById(id)
                .orElseThrow(() ->
                        new BusinessException("Usuário não encontrado"));

        Long usuarioId = usuario.getId();
        String usuarioEmail = usuario.getEmail();

        repository.delete(usuario);
        repository.flush();

        logService.registrarAuditoria(
                "EXCLUSAO_USUARIO",
                "Usuário excluído: " + usuarioEmail,
                "Usuario",
                usuarioId,
                null
        );
    }
}