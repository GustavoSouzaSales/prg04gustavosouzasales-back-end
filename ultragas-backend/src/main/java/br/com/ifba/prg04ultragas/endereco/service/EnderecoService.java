package br.com.ifba.prg04ultragas.endereco.service;

import br.com.ifba.prg04ultragas.endereco.dto.EnderecoRequestDTO;
import br.com.ifba.prg04ultragas.endereco.dto.EnderecoResponseDTO;
import br.com.ifba.prg04ultragas.endereco.model.Endereco;
import br.com.ifba.prg04ultragas.endereco.repository.EnderecoRepository;
import br.com.ifba.prg04ultragas.infrastructure.exception.BusinessException;
import br.com.ifba.prg04ultragas.usuario.model.Usuario;
import br.com.ifba.prg04ultragas.usuario.repository.UsuarioRepository;
import br.com.ifba.prg04ultragas.log.service.LogService;

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

    @Autowired
    private LogService logService;

    // Lista todos os endereços
    public Page<EnderecoResponseDTO> listarEnderecos(Pageable pageable) {
        return repository.findAll(pageable).map(this::toResponse);
    }

    // Busca um endereço pelo ID
    public EnderecoResponseDTO buscarEnderecoPorId(Long id) {
        Endereco endereco = repository.findById(id)
                .orElseThrow(() -> new BusinessException("Endereço não encontrado"));

        return toResponse(endereco);
    }

    @Transactional
    public EnderecoResponseDTO salvarEndereco(EnderecoRequestDTO dto) {

        // Verifica se o usuário existe
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new BusinessException("Usuário não encontrado"));

        // Se for principal, remove essa marcação dos outros endereços
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

        // Registra a criação no histórico
        logService.registrarAuditoria(
                "CRIACAO_ENDERECO",
                "Endereço \"" + endereco.getTitulo() + "\" criado.",
                "Endereco",
                endereco.getId(),
                usuario
        );

        return toResponse(endereco);
    }

    @Transactional
    public EnderecoResponseDTO atualizarEndereco(Long id, EnderecoRequestDTO dto) {

        Endereco endereco = repository.findById(id)
                .orElseThrow(() -> new BusinessException("Endereço não encontrado"));

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new BusinessException("Usuário não encontrado"));

        // Mantém apenas um endereço principal por usuário
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

        // Registra a atualização no histórico
        logService.registrarAuditoria(
                "ATUALIZACAO_ENDERECO",
                "Endereço \"" + endereco.getTitulo() + "\" atualizado.",
                "Endereco",
                endereco.getId(),
                usuario
        );

        return toResponse(endereco);
    }

    @Transactional
    public void deletarEndereco(Long id) {
        Endereco endereco = repository.findById(id)
                .orElseThrow(() -> new BusinessException("Endereço não encontrado"));

        Usuario usuario = endereco.getUsuario();

        // Registra a exclusão antes de apagar
        logService.registrarAuditoria(
                "EXCLUSAO_ENDERECO",
                "Endereço \"" + endereco.getTitulo() + "\" excluído.",
                "Endereco",
                endereco.getId(),
                usuario
        );

        repository.delete(endereco);
    }

    // Converte a entidade para DTO
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

    // Lista apenas os endereços de um usuário
    public Page<EnderecoResponseDTO> listarEnderecosPorUsuario(
            Long usuarioId,
            Pageable pageable
    ) {
        return repository.findByUsuarioId(usuarioId, pageable)
                .map(this::toResponse);
    }
}