package br.com.ifba.prg04ultragas.usuario.controller;

import br.com.ifba.prg04ultragas.usuario.dto.UsuarioRequestDTO;
import br.com.ifba.prg04ultragas.usuario.dto.UsuarioResponseDTO;
import br.com.ifba.prg04ultragas.usuario.service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Define essa classe como controller REST

@RequestMapping("/usuarios") // Caminho principal da API

@CrossOrigin("*") // Permite conexão com o frontend

public class UsuarioController {

    @Autowired
    private UsuarioService service;

    // Lista todos os usuários cadastrados
    @GetMapping
    public List<UsuarioResponseDTO> listarUsuarios() {

        return service.listarUsuarios();
    }

    // Salva um novo usuário no banco
    @PostMapping
    public UsuarioResponseDTO salvarUsuario(
            @RequestBody UsuarioRequestDTO dto
    ) {

        return service.salvarUsuario(dto);
    }

    // Busca usuário por ID
    @GetMapping("/{id}")
    public UsuarioResponseDTO buscarUsuarioPorId(
            @PathVariable Long id
    ) {

        return service.buscarUsuarioPorId(id);
    }

    // Atualiza os dados de um usuário existente
    @PutMapping("/{id}")
    public UsuarioResponseDTO atualizarUsuario(
            @PathVariable Long id,
            @RequestBody UsuarioRequestDTO dto
    ) {

        return service.atualizarUsuario(id, dto);
    }

    // Remove um usuário pelo ID
    @DeleteMapping("/{id}")
    public void deletarUsuario(@PathVariable Long id) {

        service.deletarUsuario(id);
    }
}