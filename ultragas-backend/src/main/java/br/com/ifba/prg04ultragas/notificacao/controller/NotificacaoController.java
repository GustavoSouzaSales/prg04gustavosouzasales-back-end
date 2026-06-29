package br.com.ifba.prg04ultragas.notificacao.controller;

import br.com.ifba.prg04ultragas.notificacao.dto.NotificacaoResponseDTO;
import br.com.ifba.prg04ultragas.notificacao.service.NotificacaoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notificacoes")
@CrossOrigin("*")
public class NotificacaoController {

    @Autowired
    private NotificacaoService service;

    @GetMapping("/usuario/{usuarioId}")
    public List<NotificacaoResponseDTO> listarPorUsuario(@PathVariable Long usuarioId) {
        return service.listarPorUsuario(usuarioId);
    }

    @PutMapping("/{id}/lida")
    public NotificacaoResponseDTO marcarComoLida(@PathVariable Long id) {
        return service.marcarComoLida(id);
    }

    @PutMapping("/usuario/{usuarioId}/marcar-todas")
    public void marcarTodasComoLidas(@PathVariable Long usuarioId) {
        service.marcarTodasComoLidas(usuarioId);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}