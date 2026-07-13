package br.com.ifba.prg04ultragas.produto.controller;

import br.com.ifba.prg04ultragas.produto.dto.ProdutoRequestDTO;
import br.com.ifba.prg04ultragas.produto.dto.ProdutoResponseDTO;
import br.com.ifba.prg04ultragas.produto.service.ProdutoService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/produtos")
@CrossOrigin("*") // Permite acesso ao front-end
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    // Lista todos os produtos
    @GetMapping
    public Page<ProdutoResponseDTO> listarProdutos(Pageable pageable) {
        return service.listarProdutos(pageable);
    }

    // Cadastra um novo produto
    @PostMapping
    public ProdutoResponseDTO salvarProduto(
            @RequestBody @Valid ProdutoRequestDTO dto
    ) {
        return service.salvarProduto(dto);
    }

    // Busca um produto pelo ID
    @GetMapping("/{id}")
    public ProdutoResponseDTO buscarProdutoPorId(
            @PathVariable Long id
    ) {
        return service.buscarProdutoPorId(id);
    }

    // Atualiza os dados de um produto
    @PutMapping("/{id}")
    public ProdutoResponseDTO atualizarProduto(
            @PathVariable Long id,
            @RequestBody @Valid ProdutoRequestDTO dto
    ) {
        return service.atualizarProduto(id, dto);
    }

    // Remove um produto
    @DeleteMapping("/{id}")
    public void deletarProduto(@PathVariable Long id) {
        service.deletarProduto(id);
    }
}