package br.com.ifba.prg04ultragas.endereco.controller;

import br.com.ifba.prg04ultragas.endereco.dto.EnderecoRequestDTO;
import br.com.ifba.prg04ultragas.endereco.dto.EnderecoResponseDTO;
import br.com.ifba.prg04ultragas.endereco.service.EnderecoService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/enderecos")
@CrossOrigin("*") // Permite acesso ao front-end
public class EnderecoController {

    @Autowired
    private EnderecoService service;

    // Lista todos os endereços
    @GetMapping
    public Page<EnderecoResponseDTO> listarEnderecos(Pageable pageable) {
        return service.listarEnderecos(pageable);
    }

    // Cadastra um novo endereço
    @PostMapping
    public EnderecoResponseDTO salvarEndereco(
            @RequestBody @Valid EnderecoRequestDTO dto
    ) {
        return service.salvarEndereco(dto);
    }

    // Lista os endereços de um usuário
    @GetMapping("/usuario/{usuarioId}")
    public Page<EnderecoResponseDTO> listarEnderecosPorUsuario(
            @PathVariable Long usuarioId,
            Pageable pageable
    ) {
        return service.listarEnderecosPorUsuario(usuarioId, pageable);
    }

    // Busca um endereço pelo ID
    @GetMapping("/{id}")
    public EnderecoResponseDTO buscarEnderecoPorId(
            @PathVariable Long id
    ) {
        return service.buscarEnderecoPorId(id);
    }

    // Atualiza um endereço
    @PutMapping("/{id}")
    public EnderecoResponseDTO atualizarEndereco(
            @PathVariable Long id,
            @RequestBody @Valid EnderecoRequestDTO dto
    ) {
        return service.atualizarEndereco(id, dto);
    }

    // Remove um endereço
    @DeleteMapping("/{id}")
    public void deletarEndereco(@PathVariable Long id) {
        service.deletarEndereco(id);
    }

}