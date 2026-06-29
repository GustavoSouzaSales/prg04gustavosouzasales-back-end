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

    public Page<LogResponseDTO> listarLogs(Pageable pageable) {
        return repository.findAll(pageable).map(this::toResponse);
    }

    public LogResponseDTO buscarLogPorId(Long id) {
        Log log = repository.findById(id)
                .orElseThrow(() -> new BusinessException("Log não encontrado"));

        return toResponse(log);
    }

    @Transactional
    public LogResponseDTO salvarLog(LogRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new BusinessException("Usuário não encontrado"));

        Log log = new Log();
        log.setAcao(dto.getAcao());
        log.setDescricao(dto.getDescricao());
        log.setIp(dto.getIp());
        log.setDataHora(LocalDateTime.now());
        log.setUsuario(usuario);

        log = repository.save(log);

        return toResponse(log);
    }

    @Transactional
    public void deletarLog(Long id) {
        Log log = repository.findById(id)
                .orElseThrow(() -> new BusinessException("Log não encontrado"));

        repository.delete(log);
    }

    private LogResponseDTO toResponse(Log log) {
        LogResponseDTO dto = new LogResponseDTO();

        dto.setId(log.getId());
        dto.setAcao(log.getAcao());
        dto.setDescricao(log.getDescricao());
        dto.setIp(log.getIp());
        dto.setDataHora(log.getDataHora());

        if (log.getUsuario() != null) {
            dto.setUsuarioId(log.getUsuario().getId());
        }

        return dto;
    }
}