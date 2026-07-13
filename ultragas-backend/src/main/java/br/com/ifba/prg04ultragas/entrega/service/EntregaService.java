package br.com.ifba.prg04ultragas.entrega.service;

import br.com.ifba.prg04ultragas.entrega.dto.EntregaRequestDTO;
import br.com.ifba.prg04ultragas.entrega.dto.EntregaResponseDTO;
import br.com.ifba.prg04ultragas.entrega.model.Entrega;
import br.com.ifba.prg04ultragas.entrega.repository.EntregaRepository;
import br.com.ifba.prg04ultragas.infrastructure.exception.BusinessException;
import br.com.ifba.prg04ultragas.pedido.model.Pedido;
import br.com.ifba.prg04ultragas.pedido.repository.PedidoRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EntregaService {

    @Autowired
    private EntregaRepository repository;

    @Autowired
    private PedidoRepository pedidoRepository;

    // Lista todas as entregas
    public Page<EntregaResponseDTO> listarEntregas(Pageable pageable) {
        return repository.findAll(pageable).map(this::toResponse);
    }

    // Busca uma entrega pelo ID
    public EntregaResponseDTO buscarEntregaPorId(Long id) {
        Entrega entrega = repository.findById(id)
                .orElseThrow(() -> new BusinessException("Entrega não encontrada"));

        return toResponse(entrega);
    }

    @Transactional
    public EntregaResponseDTO salvarEntrega(EntregaRequestDTO dto) {

        // Verifica se o pedido existe
        Pedido pedido = pedidoRepository.findById(dto.getPedidoId())
                .orElseThrow(() -> new BusinessException("Pedido não encontrado"));

        Entrega entrega = new Entrega();
        entrega.setFormaRecebimento(dto.getFormaRecebimento());
        entrega.setHorarioPreferido(dto.getHorarioPreferido());

        // Define taxa e status padrão caso não sejam informados
        entrega.setTaxaEntrega(dto.getTaxaEntrega() != null ? dto.getTaxaEntrega() : 0.0);
        entrega.setStatusEntrega(
                dto.getStatusEntrega() != null ? dto.getStatusEntrega() : "Pendente"
        );
        entrega.setPedido(pedido);

        entrega = repository.save(entrega);

        return toResponse(entrega);
    }

    @Transactional
    public EntregaResponseDTO atualizarEntrega(Long id, EntregaRequestDTO dto) {
        Entrega entrega = repository.findById(id)
                .orElseThrow(() -> new BusinessException("Entrega não encontrada"));

        Pedido pedido = pedidoRepository.findById(dto.getPedidoId())
                .orElseThrow(() -> new BusinessException("Pedido não encontrado"));

        entrega.setFormaRecebimento(dto.getFormaRecebimento());
        entrega.setHorarioPreferido(dto.getHorarioPreferido());
        entrega.setTaxaEntrega(dto.getTaxaEntrega());
        entrega.setStatusEntrega(dto.getStatusEntrega());
        entrega.setPedido(pedido);

        entrega = repository.save(entrega);

        return toResponse(entrega);
    }

    @Transactional
    public void deletarEntrega(Long id) {
        Entrega entrega = repository.findById(id)
                .orElseThrow(() -> new BusinessException("Entrega não encontrada"));

        repository.delete(entrega);
    }

    // Converte a entidade para DTO de resposta
    private EntregaResponseDTO toResponse(Entrega entrega) {
        EntregaResponseDTO dto = new EntregaResponseDTO();

        dto.setId(entrega.getId());
        dto.setFormaRecebimento(entrega.getFormaRecebimento());
        dto.setHorarioPreferido(entrega.getHorarioPreferido());
        dto.setTaxaEntrega(entrega.getTaxaEntrega());
        dto.setStatusEntrega(entrega.getStatusEntrega());

        if (entrega.getPedido() != null) {
            dto.setPedidoId(entrega.getPedido().getId());
        }

        return dto;
    }
}