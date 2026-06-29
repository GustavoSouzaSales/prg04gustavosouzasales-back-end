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
@CrossOrigin("*")
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    @GetMapping
    public Page<ProdutoResponseDTO> listarProdutos(Pageable pageable) {
        return service.listarProdutos(pageable);
    }

    @PostMapping
    public ProdutoResponseDTO salvarProduto(
            @RequestBody @Valid ProdutoRequestDTO dto
    ) {
        return service.salvarProduto(dto);
    }

    @GetMapping("/{id}")
    public ProdutoResponseDTO buscarProdutoPorId(
            @PathVariable Long id
    ) {
        return service.buscarProdutoPorId(id);
    }

    @PutMapping("/{id}")
    public ProdutoResponseDTO atualizarProduto(
            @PathVariable Long id,
            @RequestBody @Valid ProdutoRequestDTO dto
    ) {
        return service.atualizarProduto(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deletarProduto(@PathVariable Long id) {
        service.deletarProduto(id);
    }
}