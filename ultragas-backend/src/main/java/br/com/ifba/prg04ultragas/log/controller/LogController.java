package br.com.ifba.prg04ultragas.log.controller;

import br.com.ifba.prg04ultragas.log.dto.LogRequestDTO;
import br.com.ifba.prg04ultragas.log.dto.LogResponseDTO;
import br.com.ifba.prg04ultragas.log.service.LogService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/logs")
@CrossOrigin("*")
public class LogController {

    @Autowired
    private LogService service;

    @GetMapping
    public Page<LogResponseDTO> listarLogs(Pageable pageable) {
        return service.listarLogs(pageable);
    }

    @PostMapping
    public LogResponseDTO salvarLog(
            @RequestBody @Valid LogRequestDTO dto
    ) {
        return service.salvarLog(dto);
    }

    @GetMapping("/{id}")
    public LogResponseDTO buscarLogPorId(
            @PathVariable Long id
    ) {
        return service.buscarLogPorId(id);
    }

    @DeleteMapping("/{id}")
    public void deletarLog(@PathVariable Long id) {
        service.deletarLog(id);
    }
}