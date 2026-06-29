package br.com.ifba.prg04ultragas.usuario.controller;

import br.com.ifba.prg04ultragas.usuario.dto.UsuarioRequestDTO;
import br.com.ifba.prg04ultragas.usuario.dto.UsuarioResponseDTO;
import br.com.ifba.prg04ultragas.usuario.service.UsuarioService;
import br.com.ifba.prg04ultragas.usuario.dto.UsuarioUpdateDTO;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@RestController // Define essa classe como controller REST
@RequestMapping("/usuarios") // Caminho principal da API
@CrossOrigin("*") // Permite conexão com o frontend

public class UsuarioController {

    @Autowired
    private UsuarioService service;

    // Lista todos os usuários cadastrados com paginação
    @GetMapping
    public Page<UsuarioResponseDTO> listarUsuarios(
            Pageable pageable
    ) {

        return service.listarUsuarios(pageable);
    }

    // Salva um novo usuário no banco
    @PostMapping
    public UsuarioResponseDTO salvarUsuario(
            @RequestBody @Valid UsuarioRequestDTO dto
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
            @RequestBody @Valid UsuarioUpdateDTO dto
    ) {

        return service.atualizarUsuario(id, dto);
    }

    // Remove um usuário pelo ID
    @DeleteMapping("/{id}")
    public void deletarUsuario(@PathVariable Long id) {

        service.deletarUsuario(id);
    }
}