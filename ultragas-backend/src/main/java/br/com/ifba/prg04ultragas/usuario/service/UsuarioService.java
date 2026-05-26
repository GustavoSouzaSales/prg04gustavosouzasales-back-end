package br.com.ifba.prg04ultragas.usuario.service;

import br.com.ifba.prg04ultragas.infrastructure.exception.BusinessException;
import br.com.ifba.prg04ultragas.infrastructure.mapper.ObjectMapperUtil;
import br.com.ifba.prg04ultragas.usuario.dto.UsuarioRequestDTO;
import br.com.ifba.prg04ultragas.usuario.dto.UsuarioResponseDTO;
import br.com.ifba.prg04ultragas.usuario.model.Usuario;
import br.com.ifba.prg04ultragas.usuario.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private ObjectMapperUtil mapper;

    // Lista todos os usuários
    public List<UsuarioResponseDTO> listarUsuarios() {

        List<Usuario> usuarios = repository.findAll();

        return mapper.mapAll(
                usuarios,
                UsuarioResponseDTO.class
        );
    }

    // Busca usuário por ID
    public UsuarioResponseDTO buscarUsuarioPorId(Long id) {

        Usuario usuario = repository.findById(id)
                .orElseThrow(() ->
                        new BusinessException("Usuário não encontrado"));

        return mapper.map(
                usuario,
                UsuarioResponseDTO.class
        );
    }

    // Salva usuário
    @Transactional
    public UsuarioResponseDTO salvarUsuario(
            UsuarioRequestDTO dto
    ) {

        Usuario usuario = mapper.map(
                dto,
                Usuario.class
        );

        usuario = repository.save(usuario);

        return mapper.map(
                usuario,
                UsuarioResponseDTO.class
        );
    }

    // Atualiza usuário
    @Transactional
    public UsuarioResponseDTO atualizarUsuario(
            Long id,
            UsuarioRequestDTO dto
    ) {

        Usuario usuario = repository.findById(id)
                .orElseThrow(() ->
                        new BusinessException("Usuário não encontrado"));

        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());

        usuario = repository.save(usuario);

        return mapper.map(
                usuario,
                UsuarioResponseDTO.class
        );
    }

    // Remove usuário
    @Transactional
    public void deletarUsuario(Long id) {

        Usuario usuario = repository.findById(id)
                .orElseThrow(() ->
                        new BusinessException("Usuário não encontrado"));

        repository.delete(usuario);
    }
}