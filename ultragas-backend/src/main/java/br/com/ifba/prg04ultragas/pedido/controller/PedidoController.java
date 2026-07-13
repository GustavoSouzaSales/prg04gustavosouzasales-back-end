package br.com.ifba.prg04ultragas.pedido.controller;

import br.com.ifba.prg04ultragas.pedido.dto.PedidoRequestDTO;
import br.com.ifba.prg04ultragas.pedido.dto.PedidoResponseDTO;
import br.com.ifba.prg04ultragas.pedido.service.PedidoService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos")
@CrossOrigin("*") // Permite acesso ao front-end
public class PedidoController {

    @Autowired
    private PedidoService service;

    // Lista todos os pedidos
    @GetMapping
    public Page<PedidoResponseDTO> listarPedidos(Pageable pageable) {
        return service.listarPedidos(pageable);
    }

    // Cadastra um novo pedido
    @PostMapping
    public PedidoResponseDTO salvarPedido(
            @RequestBody @Valid PedidoRequestDTO dto
    ) {
        return service.salvarPedido(dto);
    }

    // Lista os pedidos de um usuário específico
    @GetMapping("/usuario/{usuarioId}")
    public Page<PedidoResponseDTO> listarPedidosPorUsuario(
            @PathVariable Long usuarioId,
            Pageable pageable
    ) {
        return service.listarPedidosPorUsuario(usuarioId, pageable);
    }

    // Busca um pedido pelo ID
    @GetMapping("/{id}")
    public PedidoResponseDTO buscarPedidoPorId(
            @PathVariable Long id
    ) {
        return service.buscarPedidoPorId(id);
    }

    // Atualiza um pedido
    @PutMapping("/{id}")
    public PedidoResponseDTO atualizarPedido(
            @PathVariable Long id,
            @RequestBody @Valid PedidoRequestDTO dto
    ) {
        return service.atualizarPedido(id, dto);
    }

    // Remove um pedido
    @DeleteMapping("/{id}")
    public void deletarPedido(@PathVariable Long id) {
        service.deletarPedido(id);
    }
}