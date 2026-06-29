package br.com.ifba.prg04ultragas.itempedido.controller;

import br.com.ifba.prg04ultragas.itempedido.dto.ItemPedidoRequestDTO;
import br.com.ifba.prg04ultragas.itempedido.dto.ItemPedidoResponseDTO;
import br.com.ifba.prg04ultragas.itempedido.service.ItemPedidoService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/itens-pedido")
@CrossOrigin("*")
public class ItemPedidoController {

    @Autowired
    private ItemPedidoService service;

    @GetMapping
    public Page<ItemPedidoResponseDTO> listarItens(Pageable pageable) {
        return service.listarItens(pageable);
    }

    @PostMapping
    public ItemPedidoResponseDTO salvarItem(
            @RequestBody @Valid ItemPedidoRequestDTO dto
    ) {
        return service.salvarItem(dto);
    }

    @GetMapping("/pedido/{pedidoId}")
    public Page<ItemPedidoResponseDTO> listarItensPorPedido(
            @PathVariable Long pedidoId,
            Pageable pageable
    ) {
        return service.listarItensPorPedido(pedidoId, pageable);
    }

    @GetMapping("/{id}")
    public ItemPedidoResponseDTO buscarItemPorId(
            @PathVariable Long id
    ) {
        return service.buscarItemPorId(id);
    }

    @PutMapping("/{id}")
    public ItemPedidoResponseDTO atualizarItem(
            @PathVariable Long id,
            @RequestBody @Valid ItemPedidoRequestDTO dto
    ) {
        return service.atualizarItem(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deletarItem(@PathVariable Long id) {
        service.deletarItem(id);
    }
}