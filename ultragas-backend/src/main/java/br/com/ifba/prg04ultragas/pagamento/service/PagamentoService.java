package br.com.ifba.prg04ultragas.pagamento.service;

import br.com.ifba.prg04ultragas.infrastructure.exception.BusinessException;
import br.com.ifba.prg04ultragas.pagamento.dto.PagamentoRequestDTO;
import br.com.ifba.prg04ultragas.pagamento.dto.PagamentoResponseDTO;
import br.com.ifba.prg04ultragas.pagamento.model.Pagamento;
import br.com.ifba.prg04ultragas.pagamento.repository.PagamentoRepository;
import br.com.ifba.prg04ultragas.pedido.model.Pedido;
import br.com.ifba.prg04ultragas.pedido.repository.PedidoRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository repository;

    @Autowired
    private PedidoRepository pedidoRepository;

    public Page<PagamentoResponseDTO> listarPagamentos(Pageable pageable) {
        return repository.findAll(pageable).map(this::toResponse);
    }

    public PagamentoResponseDTO buscarPagamentoPorId(Long id) {
        Pagamento pagamento = repository.findById(id)
                .orElseThrow(() -> new BusinessException("Pagamento não encontrado"));

        return toResponse(pagamento);
    }

    @Transactional
    public PagamentoResponseDTO salvarPagamento(PagamentoRequestDTO dto) {
        Pedido pedido = pedidoRepository.findById(dto.getPedidoId())
                .orElseThrow(() -> new BusinessException("Pedido não encontrado"));

        Pagamento pagamento = new Pagamento();
        pagamento.setFormaPagamento(dto.getFormaPagamento());
        pagamento.setStatusPagamento(
                dto.getStatusPagamento() != null ? dto.getStatusPagamento() : "Pendente"
        );
        pagamento.setValorPago(dto.getValorPago());
        pagamento.setValorTroco(dto.getValorTroco());
        pagamento.setPedido(pedido);

        pagamento = repository.save(pagamento);

        return toResponse(pagamento);
    }

    @Transactional
    public PagamentoResponseDTO atualizarPagamento(Long id, PagamentoRequestDTO dto) {
        Pagamento pagamento = repository.findById(id)
                .orElseThrow(() -> new BusinessException("Pagamento não encontrado"));

        Pedido pedido = pedidoRepository.findById(dto.getPedidoId())
                .orElseThrow(() -> new BusinessException("Pedido não encontrado"));

        pagamento.setFormaPagamento(dto.getFormaPagamento());
        pagamento.setStatusPagamento(dto.getStatusPagamento());
        pagamento.setValorPago(dto.getValorPago());
        pagamento.setValorTroco(dto.getValorTroco());
        pagamento.setPedido(pedido);

        pagamento = repository.save(pagamento);

        return toResponse(pagamento);
    }

    @Transactional
    public void deletarPagamento(Long id) {
        Pagamento pagamento = repository.findById(id)
                .orElseThrow(() -> new BusinessException("Pagamento não encontrado"));

        repository.delete(pagamento);
    }

    private PagamentoResponseDTO toResponse(Pagamento pagamento) {
        PagamentoResponseDTO dto = new PagamentoResponseDTO();

        dto.setId(pagamento.getId());
        dto.setFormaPagamento(pagamento.getFormaPagamento());
        dto.setStatusPagamento(pagamento.getStatusPagamento());
        dto.setValorPago(pagamento.getValorPago());
        dto.setValorTroco(pagamento.getValorTroco());

        if (pagamento.getPedido() != null) {
            dto.setPedidoId(pagamento.getPedido().getId());
        }

        return dto;
    }
}