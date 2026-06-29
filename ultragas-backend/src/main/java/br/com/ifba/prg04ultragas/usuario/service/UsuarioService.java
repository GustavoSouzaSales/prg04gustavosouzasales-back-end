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

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private ObjectMapperUtil mapper;

    public Page<UsuarioResponseDTO> listarUsuarios(Pageable pageable) {

        Page<Usuario> usuarios = repository.findAll(pageable);

        return usuarios.map(usuario ->
                mapper.map(usuario, UsuarioResponseDTO.class)
        );
    }

    public UsuarioResponseDTO buscarUsuarioPorId(Long id) {

        Usuario usuario = repository.findById(id)
                .orElseThrow(() ->
                        new BusinessException("Usuário não encontrado"));

        return mapper.map(usuario, UsuarioResponseDTO.class);
    }

    @Transactional
    public UsuarioResponseDTO salvarUsuario(UsuarioRequestDTO dto) {

        Usuario usuario = mapper.map(dto, Usuario.class);

        if (usuario.getStatus() == null || usuario.getStatus().isBlank()) {
            usuario.setStatus("Ativo");
        }

        if (usuario.getTipoUsuario() == null || usuario.getTipoUsuario().isBlank()) {
            usuario.setTipoUsuario("CLIENTE");
        }

        usuario = repository.save(usuario);

        return mapper.map(usuario, UsuarioResponseDTO.class);
    }

    @Transactional
    public UsuarioResponseDTO atualizarUsuario(Long id, UsuarioUpdateDTO dto) {

        Usuario usuario = repository.findById(id)
                .orElseThrow(() ->
                        new BusinessException("Usuário não encontrado"));

        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setTelefone(dto.getTelefone());
        usuario.setStatus(dto.getStatus());
        usuario.setTipoUsuario(dto.getTipoUsuario());

        if (dto.getNovaSenha() != null && !dto.getNovaSenha().isBlank()) {

            if (dto.getSenhaAtual() == null || !dto.getSenhaAtual().equals(usuario.getSenha())) {
                throw new BusinessException("Senha atual inválida");
            }

            usuario.setSenha(dto.getNovaSenha());
        }

        usuario = repository.save(usuario);

        return mapper.map(usuario, UsuarioResponseDTO.class);
    }

    @Transactional
    public void deletarUsuario(Long id) {

        Usuario usuario = repository.findById(id)
                .orElseThrow(() ->
                        new BusinessException("Usuário não encontrado"));

        repository.delete(usuario);
    }
}