package br.com.ifba.prg04ultragas.entrega.controller;

import br.com.ifba.prg04ultragas.entrega.dto.EntregaRequestDTO;
import br.com.ifba.prg04ultragas.entrega.dto.EntregaResponseDTO;
import br.com.ifba.prg04ultragas.entrega.service.EntregaService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/entregas")
@CrossOrigin("*") // Permite acesso ao front-end
public class EntregaController {

    @Autowired
    private EntregaService service;

    // Lista todas as entregas
    @GetMapping
    public Page<EntregaResponseDTO> listarEntregas(Pageable pageable) {
        return service.listarEntregas(pageable);
    }

    // Cadastra uma nova entrega
    @PostMapping
    public EntregaResponseDTO salvarEntrega(
            @RequestBody @Valid EntregaRequestDTO dto
    ) {
        return service.salvarEntrega(dto);
    }

    // Busca uma entrega pelo ID
    @GetMapping("/{id}")
    public EntregaResponseDTO buscarEntregaPorId(
            @PathVariable Long id
    ) {
        return service.buscarEntregaPorId(id);
    }

    // Atualiza uma entrega
    @PutMapping("/{id}")
    public EntregaResponseDTO atualizarEntrega(
            @PathVariable Long id,
            @RequestBody @Valid EntregaRequestDTO dto
    ) {
        return service.atualizarEntrega(id, dto);
    }

    // Remove uma entrega
    @DeleteMapping("/{id}")
    public void deletarEntrega(@PathVariable Long id) {
        service.deletarEntrega(id);
    }
}