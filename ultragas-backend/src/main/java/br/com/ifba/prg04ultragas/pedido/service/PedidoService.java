package br.com.ifba.prg04ultragas.pedido.service;

import br.com.ifba.prg04ultragas.endereco.model.Endereco;
import br.com.ifba.prg04ultragas.endereco.repository.EnderecoRepository;
import br.com.ifba.prg04ultragas.infrastructure.exception.BusinessException;
import br.com.ifba.prg04ultragas.pedido.dto.PedidoRequestDTO;
import br.com.ifba.prg04ultragas.pedido.dto.PedidoResponseDTO;
import br.com.ifba.prg04ultragas.pedido.model.Pedido;
import br.com.ifba.prg04ultragas.pedido.repository.PedidoRepository;
import br.com.ifba.prg04ultragas.usuario.model.Usuario;
import br.com.ifba.prg04ultragas.usuario.repository.UsuarioRepository;
import br.com.ifba.prg04ultragas.notificacao.service.NotificacaoService;
import br.com.ifba.prg04ultragas.log.service.LogService;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private NotificacaoService notificacaoService;

    @Autowired
    private LogService logService;

    public Page<PedidoResponseDTO> listarPedidos(Pageable pageable) {
        return repository.findAll(pageable).map(this::toResponse);
    }

    public PedidoResponseDTO buscarPedidoPorId(Long id) {
        Pedido pedido = repository.findById(id)
                .orElseThrow(() -> new BusinessException("Pedido não encontrado"));

        return toResponse(pedido);
    }

    @Transactional
    public PedidoResponseDTO salvarPedido(PedidoRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new BusinessException("Usuário não encontrado"));

        Endereco endereco = null;

        if (dto.getEnderecoId() != null) {
            endereco = enderecoRepository.findById(dto.getEnderecoId())
                    .orElseThrow(() -> new BusinessException("Endereço não encontrado"));
        }

        Pedido pedido = new Pedido();
        pedido.setCodigo("PED-" + System.currentTimeMillis());
        pedido.setDataPedido(LocalDateTime.now());
        pedido.setStatus(dto.getStatus() != null ? dto.getStatus() : "Pendente");
        pedido.setValorTotal(dto.getValorTotal() != null ? dto.getValorTotal() : 0.0);
        pedido.setUsuario(usuario);
        pedido.setEndereco(endereco);
        pedido.setFormaPagamento(dto.getFormaPagamento());
        pedido.setFormaRecebimento(dto.getFormaRecebimento());
        pedido.setNomeContato(dto.getNomeContato());
        pedido.setTelefoneContato(dto.getTelefoneContato());

        pedido = repository.save(pedido);

        notificacaoService.criarNotificacao(
                usuario.getId(),
                "Pedido criado",
                "Seu pedido " + pedido.getCodigo() + " foi criado com sucesso.",
                "PEDIDO"
        );

        logService.registrarAuditoria(
                "CRIACAO_PEDIDO",
                "Pedido " + pedido.getCodigo() + " criado com sucesso.",
                "Pedido",
                pedido.getId(),
                usuario
        );

        return toResponse(pedido);
    }

    @Transactional
    public PedidoResponseDTO atualizarPedido(Long id, PedidoRequestDTO dto) {
        Pedido pedido = repository.findById(id)
                .orElseThrow(() -> new BusinessException("Pedido não encontrado"));

        String statusAnterior = pedido.getStatus();

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new BusinessException("Usuário não encontrado"));

        Endereco endereco = null;

        if (dto.getEnderecoId() != null) {
            endereco = enderecoRepository.findById(dto.getEnderecoId())
                    .orElseThrow(() -> new BusinessException("Endereço não encontrado"));
        }

        pedido.setEndereco(endereco);

        pedido.setStatus(dto.getStatus());
        pedido.setValorTotal(dto.getValorTotal());
        pedido.setUsuario(usuario);
        pedido.setEndereco(endereco);
        pedido.setFormaRecebimento(dto.getFormaRecebimento());
        pedido.setFormaPagamento(dto.getFormaPagamento());
        pedido.setNomeContato(dto.getNomeContato());
        pedido.setTelefoneContato(dto.getTelefoneContato());

        pedido = repository.save(pedido);

        if (dto.getStatus() != null &&
                !dto.getStatus().equalsIgnoreCase(statusAnterior)) {

            String acao = "Cancelado".equalsIgnoreCase(dto.getStatus())
                    ? "CANCELAMENTO_PEDIDO"
                    : "ALTERACAO_STATUS_PEDIDO";

            logService.registrarAuditoria(
                    acao,
                    "Status do pedido " + pedido.getCodigo()
                            + " alterado de " + statusAnterior
                            + " para " + pedido.getStatus() + ".",
                    "Pedido",
                    pedido.getId(),
                    usuario
            );
        }

        if ("Em entrega".equalsIgnoreCase(pedido.getStatus())) {
            notificacaoService.criarNotificacao(
                    usuario.getId(),
                    "Pedido em entrega",
                    "Seu pedido " + pedido.getCodigo() + " saiu para entrega.",
                    "PEDIDO"
            );
        }

        if ("Concluído".equalsIgnoreCase(pedido.getStatus())) {
            notificacaoService.criarNotificacao(
                    usuario.getId(),
                    "Pedido concluído",
                    "Seu pedido " + pedido.getCodigo() + " foi concluído com sucesso.",
                    "PEDIDO"
            );
        }

        if ("Cancelado".equalsIgnoreCase(pedido.getStatus())) {
            notificacaoService.criarNotificacao(
                    usuario.getId(),
                    "Pedido cancelado",
                    "Seu pedido " + pedido.getCodigo() + " foi cancelado.",
                    "PEDIDO"
            );
        }

        return toResponse(pedido);
    }

    @Transactional
    public void deletarPedido(Long id) {

        Pedido pedido = repository.findById(id)
                .orElseThrow(() ->
                        new BusinessException("Pedido não encontrado"));

        Usuario usuario = pedido.getUsuario();

        logService.registrarAuditoria(
                "EXCLUSAO_PEDIDO",
                "Pedido " + pedido.getCodigo() + " excluído.",
                "Pedido",
                pedido.getId(),
                usuario
        );

        repository.delete(pedido);
    }

    private PedidoResponseDTO toResponse(Pedido pedido) {
        PedidoResponseDTO dto = new PedidoResponseDTO();

        dto.setId(pedido.getId());
        dto.setCodigo(pedido.getCodigo());
        dto.setDataPedido(pedido.getDataPedido());
        dto.setStatus(pedido.getStatus());
        dto.setValorTotal(pedido.getValorTotal());
        dto.setFormaPagamento(pedido.getFormaPagamento());
        dto.setFormaRecebimento(pedido.getFormaRecebimento());
        dto.setNomeContato(pedido.getNomeContato());
        dto.setTelefoneContato(pedido.getTelefoneContato());

        if (pedido.getUsuario() != null) {
            dto.setUsuarioId(pedido.getUsuario().getId());
        }

        if (pedido.getEndereco() != null) {
            dto.setEnderecoId(pedido.getEndereco().getId());
        }

        return dto;
    }

    public Page<PedidoResponseDTO> listarPedidosPorUsuario(
            Long usuarioId,
            Pageable pageable
    ) {
        return repository.findByUsuarioId(usuarioId, pageable)
                .map(this::toResponse);
    }
}