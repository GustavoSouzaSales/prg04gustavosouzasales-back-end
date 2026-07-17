package br.com.ifba.prg04ultragas.log.service;

import br.com.ifba.prg04ultragas.infrastructure.exception.BusinessException;
import br.com.ifba.prg04ultragas.log.dto.LogRequestDTO;
import br.com.ifba.prg04ultragas.log.dto.LogResponseDTO;
import br.com.ifba.prg04ultragas.log.model.Log;
import br.com.ifba.prg04ultragas.log.repository.LogRepository;
import br.com.ifba.prg04ultragas.usuario.model.Usuario;
import br.com.ifba.prg04ultragas.usuario.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LogService {

    @Autowired
    private LogRepository repository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Lista todos os logs
    public Page<LogResponseDTO> listarLogs(Pageable pageable) {
        return repository.findAll(pageable).map(this::toResponse);
    }

    // Busca um log pelo ID
    public LogResponseDTO buscarLogPorId(Long id) {
        Log log = repository.findById(id)
                .orElseThrow(() -> new BusinessException("Log não encontrado"));

        return toResponse(log);
    }

    @Transactional
    public LogResponseDTO salvarLog(LogRequestDTO dto) {

        // Verifica se o usuário existe
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new BusinessException("Usuário não encontrado"));

        Log log = new Log();

        log.setAcao(dto.getAcao());
        log.setDescricao(dto.getDescricao());
        log.setEntidade(dto.getEntidade());
        log.setEntidadeId(dto.getEntidadeId());

        log.setUsuarioNome(usuario.getNome());
        log.setUsuarioEmail(usuario.getEmail());
        log.setUsuario(usuario);

        log.setIp(dto.getIp());
        log.setDataHora(LocalDateTime.now());

        log = repository.save(log);

        return toResponse(log);
    }

    @Transactional
    public void deletarLog(Long id) {
        Log log = repository.findById(id)
                .orElseThrow(() -> new BusinessException("Log não encontrado"));

        repository.delete(log);
    }

    // Converte a entidade para DTO
    private LogResponseDTO toResponse(Log log) {
        LogResponseDTO dto = new LogResponseDTO();

        dto.setId(log.getId());
        dto.setAcao(log.getAcao());
        dto.setDescricao(log.getDescricao());
        dto.setEntidade(log.getEntidade());
        dto.setEntidadeId(log.getEntidadeId());
        dto.setUsuarioNome(log.getUsuarioNome());
        dto.setUsuarioEmail(log.getUsuarioEmail());
        dto.setIp(log.getIp());
        dto.setDataHora(log.getDataHora());

        if (log.getUsuario() != null) {
            dto.setUsuarioId(log.getUsuario().getId());
        }

        return dto;
    }

    @Transactional
    public void registrarAuditoria(
            String acao,
            String descricao,
            String entidade,
            Long entidadeId,
            Usuario usuario
    ) {

        // Registra automaticamente ações importantes do sistema
        Log log = new Log();

        log.setAcao(acao);
        log.setDescricao(descricao);
        log.setEntidade(entidade);
        log.setEntidadeId(entidadeId);
        log.setDataHora(LocalDateTime.now());

        if (usuario != null) {
            log.setUsuario(usuario);
            log.setUsuarioNome(usuario.getNome());
            log.setUsuarioEmail(usuario.getEmail());
        }

        repository.save(log);
    }

    @Transactional
    public void registrarExclusaoUsuario(
            Long usuarioId,
            String usuarioNome,
            String usuarioEmail
    ) {

        Log log = new Log();

        log.setAcao("EXCLUSAO_USUARIO");
        log.setDescricao("Usuário excluído: " + usuarioNome);
        log.setEntidade("Usuario");
        log.setEntidadeId(usuarioId);
        log.setDataHora(LocalDateTime.now());

        // Guarda os dados históricos sem criar vínculo com o usuário
        log.setUsuario(null);
        log.setUsuarioNome(usuarioNome);
        log.setUsuarioEmail(usuarioEmail);

        repository.save(log);
    }
}