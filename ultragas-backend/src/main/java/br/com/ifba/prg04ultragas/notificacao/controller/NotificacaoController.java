package br.com.ifba.prg04ultragas.notificacao.controller;

import br.com.ifba.prg04ultragas.notificacao.dto.NotificacaoResponseDTO;
import br.com.ifba.prg04ultragas.notificacao.service.NotificacaoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notificacoes")
@CrossOrigin("*") // Permite acesso ao front-end
public class NotificacaoController {

    @Autowired
    private NotificacaoService service;

    // Lista as notificações de um usuário
    @GetMapping("/usuario/{usuarioId}")
    public List<NotificacaoResponseDTO> listarPorUsuario(@PathVariable Long usuarioId) {
        return service.listarPorUsuario(usuarioId);
    }

    // Marca uma notificação como lida
    @PutMapping("/{id}/lida")
    public NotificacaoResponseDTO marcarComoLida(@PathVariable Long id) {
        return service.marcarComoLida(id);
    }

    // Marca todas as notificações do usuário como lidas
    @PutMapping("/usuario/{usuarioId}/marcar-todas")
    public void marcarTodasComoLidas(@PathVariable Long usuarioId) {
        service.marcarTodasComoLidas(usuarioId);
    }

    // Remove uma notificação
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}