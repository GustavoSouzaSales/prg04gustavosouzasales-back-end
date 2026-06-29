package br.com.ifba.prg04ultragas.pagamento.controller;

import br.com.ifba.prg04ultragas.pagamento.dto.PagamentoRequestDTO;
import br.com.ifba.prg04ultragas.pagamento.dto.PagamentoResponseDTO;
import br.com.ifba.prg04ultragas.pagamento.service.PagamentoService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pagamentos")
@CrossOrigin("*")
public class PagamentoController {

    @Autowired
    private PagamentoService service;

    @GetMapping
    public Page<PagamentoResponseDTO> listarPagamentos(Pageable pageable) {
        return service.listarPagamentos(pageable);
    }

    @PostMapping
    public PagamentoResponseDTO salvarPagamento(
            @RequestBody @Valid PagamentoRequestDTO dto
    ) {
        return service.salvarPagamento(dto);
    }

    @GetMapping("/{id}")
    public PagamentoResponseDTO buscarPagamentoPorId(
            @PathVariable Long id
    ) {
        return service.buscarPagamentoPorId(id);
    }

    @PutMapping("/{id}")
    public PagamentoResponseDTO atualizarPagamento(
            @PathVariable Long id,
            @RequestBody @Valid PagamentoRequestDTO dto
    ) {
        return service.atualizarPagamento(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deletarPagamento(@PathVariable Long id) {
        service.deletarPagamento(id);
    }
}