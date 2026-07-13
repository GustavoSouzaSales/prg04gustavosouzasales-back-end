package br.com.ifba.prg04ultragas.itempedido.service;

import br.com.ifba.prg04ultragas.infrastructure.exception.BusinessException;
import br.com.ifba.prg04ultragas.itempedido.dto.ItemPedidoRequestDTO;
import br.com.ifba.prg04ultragas.itempedido.dto.ItemPedidoResponseDTO;
import br.com.ifba.prg04ultragas.itempedido.model.ItemPedido;
import br.com.ifba.prg04ultragas.itempedido.repository.ItemPedidoRepository;
import br.com.ifba.prg04ultragas.pedido.model.Pedido;
import br.com.ifba.prg04ultragas.pedido.repository.PedidoRepository;
import br.com.ifba.prg04ultragas.produto.model.Produto;
import br.com.ifba.prg04ultragas.produto.repository.ProdutoRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ItemPedidoService {

    @Autowired
    private ItemPedidoRepository repository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    // Lista todos os itens de pedido
    public Page<ItemPedidoResponseDTO> listarItens(Pageable pageable) {
        return repository.findAll(pageable).map(this::toResponse);
    }

    // Busca um item pelo ID
    public ItemPedidoResponseDTO buscarItemPorId(Long id) {
        ItemPedido item = repository.findById(id)
                .orElseThrow(() -> new BusinessException("Item do pedido não encontrado"));

        return toResponse(item);
    }

    @Transactional
    public ItemPedidoResponseDTO salvarItem(ItemPedidoRequestDTO dto) {

        // Verifica se o pedido e o produto existem
        Pedido pedido = pedidoRepository.findById(dto.getPedidoId())
                .orElseThrow(() -> new BusinessException("Pedido não encontrado"));

        Produto produto = produtoRepository.findById(dto.getProdutoId())
                .orElseThrow(() -> new BusinessException("Produto não encontrado"));

        // Calcula os valores do item
        Double valorUnitario = produto.getPreco();
        Double subtotal = valorUnitario * dto.getQuantidade();

        ItemPedido item = new ItemPedido();
        item.setQuantidade(dto.getQuantidade());
        item.setValorUnitario(valorUnitario);
        item.setSubtotal(subtotal);
        item.setPedido(pedido);
        item.setProduto(produto);

        item = repository.save(item);

        return toResponse(item);
    }

    @Transactional
    public ItemPedidoResponseDTO atualizarItem(Long id, ItemPedidoRequestDTO dto) {
        ItemPedido item = repository.findById(id)
                .orElseThrow(() -> new BusinessException("Item do pedido não encontrado"));

        Pedido pedido = pedidoRepository.findById(dto.getPedidoId())
                .orElseThrow(() -> new BusinessException("Pedido não encontrado"));

        Produto produto = produtoRepository.findById(dto.getProdutoId())
                .orElseThrow(() -> new BusinessException("Produto não encontrado"));

        // Recalcula os valores após a atualização
        Double valorUnitario = produto.getPreco();
        Double subtotal = valorUnitario * dto.getQuantidade();

        item.setQuantidade(dto.getQuantidade());
        item.setValorUnitario(valorUnitario);
        item.setSubtotal(subtotal);
        item.setPedido(pedido);
        item.setProduto(produto);

        item = repository.save(item);

        return toResponse(item);
    }

    @Transactional
    public void deletarItem(Long id) {
        ItemPedido item = repository.findById(id)
                .orElseThrow(() -> new BusinessException("Item do pedido não encontrado"));

        repository.delete(item);
    }

    // Converte a entidade para DTO
    private ItemPedidoResponseDTO toResponse(ItemPedido item) {
        ItemPedidoResponseDTO dto = new ItemPedidoResponseDTO();

        dto.setId(item.getId());
        dto.setQuantidade(item.getQuantidade());
        dto.setValorUnitario(item.getValorUnitario());
        dto.setSubtotal(item.getSubtotal());

        if (item.getPedido() != null) {
            dto.setPedidoId(item.getPedido().getId());
        }

        if (item.getProduto() != null) {
            dto.setProdutoId(item.getProduto().getId());
            dto.setNomeProduto(item.getProduto().getNome());
            dto.setCorProduto(item.getProduto().getCor());
            dto.setPesoProduto(item.getProduto().getPeso());
        }

        return dto;
    }

    // Lista os itens de um pedido específico
    public Page<ItemPedidoResponseDTO> listarItensPorPedido(
            Long pedidoId,
            Pageable pageable
    ) {
        return repository.findByPedidoId(pedidoId, pageable)
                .map(this::toResponse);
    }
}