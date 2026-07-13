package br.com.ifba.prg04ultragas.notificacao.service;

import br.com.ifba.prg04ultragas.infrastructure.exception.BusinessException;
import br.com.ifba.prg04ultragas.notificacao.dto.NotificacaoResponseDTO;
import br.com.ifba.prg04ultragas.notificacao.model.Notificacao;
import br.com.ifba.prg04ultragas.notificacao.repository.NotificacaoRepository;
import br.com.ifba.prg04ultragas.usuario.model.Usuario;
import br.com.ifba.prg04ultragas.usuario.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificacaoService {

    @Autowired
    private NotificacaoRepository repository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Lista as notificações de um usuário
    public List<NotificacaoResponseDTO> listarPorUsuario(Long usuarioId) {
        return repository.findByUsuarioIdOrderByDataCriacaoDesc(usuarioId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public NotificacaoResponseDTO criarNotificacao(
            Long usuarioId,
            String titulo,
            String mensagem,
            String tipo
    ) {
        // Verifica se o usuário existe
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new BusinessException("Usuário não encontrado."));

        Notificacao notificacao = new Notificacao();
        notificacao.setTitulo(titulo);
        notificacao.setMensagem(mensagem);
        notificacao.setTipo(tipo);
        notificacao.setLida(false);
        notificacao.setDataCriacao(LocalDateTime.now());
        notificacao.setUsuario(usuario);

        notificacao = repository.save(notificacao);

        return toResponse(notificacao);
    }

    @Transactional
    public NotificacaoResponseDTO marcarComoLida(Long id) {
        Notificacao notificacao = repository.findById(id)
                .orElseThrow(() -> new BusinessException("Notificação não encontrada."));


        notificacao.setLida(true);
        notificacao = repository.save(notificacao);

        return toResponse(notificacao);
    }

    @Transactional
    public void marcarTodasComoLidas(Long usuarioId) {
        List<Notificacao> notificacoes = repository.findByUsuarioIdOrderByDataCriacaoDesc(usuarioId);

        // Marca todas como lidas
        notificacoes.forEach((n) -> n.setLida(true));

        repository.saveAll(notificacoes);
    }

    @Transactional
    public void deletar(Long id) {
        Notificacao notificacao = repository.findById(id)
                .orElseThrow(() -> new BusinessException("Notificação não encontrada."));

        repository.delete(notificacao);
    }

    // Converte a entidade para DTO
    private NotificacaoResponseDTO toResponse(Notificacao notificacao) {
        NotificacaoResponseDTO dto = new NotificacaoResponseDTO();

        dto.setId(notificacao.getId());
        dto.setTitulo(notificacao.getTitulo());
        dto.setMensagem(notificacao.getMensagem());
        dto.setTipo(notificacao.getTipo());
        dto.setLida(notificacao.getLida());
        dto.setDataCriacao(notificacao.getDataCriacao());

        if (notificacao.getUsuario() != null) {
            dto.setUsuarioId(notificacao.getUsuario().getId());
        }

        return dto;
    }
}