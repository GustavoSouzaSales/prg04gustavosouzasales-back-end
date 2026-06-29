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
@CrossOrigin("*")
public class EnderecoController {

    @Autowired
    private EnderecoService service;

    @GetMapping
    public Page<EnderecoResponseDTO> listarEnderecos(Pageable pageable) {
        return service.listarEnderecos(pageable);
    }

    @PostMapping
    public EnderecoResponseDTO salvarEndereco(
            @RequestBody @Valid EnderecoRequestDTO dto
    ) {
        return service.salvarEndereco(dto);
    }

    @GetMapping("/usuario/{usuarioId}")
    public Page<EnderecoResponseDTO> listarEnderecosPorUsuario(
            @PathVariable Long usuarioId,
            Pageable pageable
    ) {
        return service.listarEnderecosPorUsuario(usuarioId, pageable);
    }

    @GetMapping("/{id}")
    public EnderecoResponseDTO buscarEnderecoPorId(
            @PathVariable Long id
    ) {
        return service.buscarEnderecoPorId(id);
    }

    @PutMapping("/{id}")
    public EnderecoResponseDTO atualizarEndereco(
            @PathVariable Long id,
            @RequestBody @Valid EnderecoRequestDTO dto
    ) {
        return service.atualizarEndereco(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deletarEndereco(@PathVariable Long id) {
        service.deletarEndereco(id);
    }

}