package br.com.ifba.prg04ultragas.endereco.service;

import br.com.ifba.prg04ultragas.endereco.dto.EnderecoRequestDTO;
import br.com.ifba.prg04ultragas.endereco.dto.EnderecoResponseDTO;
import br.com.ifba.prg04ultragas.endereco.model.Endereco;
import br.com.ifba.prg04ultragas.endereco.repository.EnderecoRepository;
import br.com.ifba.prg04ultragas.infrastructure.exception.BusinessException;
import br.com.ifba.prg04ultragas.usuario.model.Usuario;
import br.com.ifba.prg04ultragas.usuario.repository.UsuarioRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository repository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Page<EnderecoResponseDTO> listarEnderecos(Pageable pageable) {
        return repository.findAll(pageable).map(this::toResponse);
    }

    public EnderecoResponseDTO buscarEnderecoPorId(Long id) {
        Endereco endereco = repository.findById(id)
                .orElseThrow(() -> new BusinessException("Endereço não encontrado"));

        return toResponse(endereco);
    }

    @Transactional
    public EnderecoResponseDTO salvarEndereco(EnderecoRequestDTO dto) {

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new BusinessException("Usuário não encontrado"));

        if (Boolean.TRUE.equals(dto.getPrincipal())) {
            repository.findByUsuarioId(dto.getUsuarioId(), Pageable.unpaged())
                    .forEach(e -> {
                        e.setPrincipal(false);
                        repository.save(e);
                    });
        }

        Endereco endereco = new Endereco();
        endereco.setTitulo(dto.getTitulo());
        endereco.setCep(dto.getCep());
        endereco.setEndereco(dto.getEndereco());
        endereco.setNumero(dto.getNumero());
        endereco.setBairro(dto.getBairro());
        endereco.setCidade(dto.getCidade());
        endereco.setUf(dto.getUf());
        endereco.setPrincipal(dto.getPrincipal());
        endereco.setUsuario(usuario);

        endereco = repository.save(endereco);

        return toResponse(endereco);
    }

    @Transactional
    public EnderecoResponseDTO atualizarEndereco(Long id, EnderecoRequestDTO dto) {

        Endereco endereco = repository.findById(id)
                .orElseThrow(() -> new BusinessException("Endereço não encontrado"));

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new BusinessException("Usuário não encontrado"));

        if (Boolean.TRUE.equals(dto.getPrincipal())) {
            repository.findByUsuarioId(dto.getUsuarioId(), Pageable.unpaged())
                    .forEach(e -> {
                        if (!e.getId().equals(id)) {
                            e.setPrincipal(false);
                            repository.save(e);
                        }
                    });
        }

        endereco.setTitulo(dto.getTitulo());
        endereco.setCep(dto.getCep());
        endereco.setEndereco(dto.getEndereco());
        endereco.setNumero(dto.getNumero());
        endereco.setBairro(dto.getBairro());
        endereco.setCidade(dto.getCidade());
        endereco.setUf(dto.getUf());
        endereco.setPrincipal(dto.getPrincipal());
        endereco.setUsuario(usuario);

        endereco = repository.save(endereco);

        return toResponse(endereco);
    }

    @Transactional
    public void deletarEndereco(Long id) {
        Endereco endereco = repository.findById(id)
                .orElseThrow(() -> new BusinessException("Endereço não encontrado"));

        repository.delete(endereco);
    }

    private EnderecoResponseDTO toResponse(Endereco endereco) {
        EnderecoResponseDTO dto = new EnderecoResponseDTO();

        dto.setId(endereco.getId());
        dto.setTitulo(endereco.getTitulo());
        dto.setCep(endereco.getCep());
        dto.setEndereco(endereco.getEndereco());
        dto.setNumero(endereco.getNumero());
        dto.setBairro(endereco.getBairro());
        dto.setCidade(endereco.getCidade());
        dto.setUf(endereco.getUf());
        dto.setPrincipal(endereco.getPrincipal());

        if (endereco.getUsuario() != null) {
            dto.setUsuarioId(endereco.getUsuario().getId());
        }

        return dto;
    }

    public Page<EnderecoResponseDTO> listarEnderecosPorUsuario(
            Long usuarioId,
            Pageable pageable
    ) {
        return repository.findByUsuarioId(usuarioId, pageable)
                .map(this::toResponse);
    }
}