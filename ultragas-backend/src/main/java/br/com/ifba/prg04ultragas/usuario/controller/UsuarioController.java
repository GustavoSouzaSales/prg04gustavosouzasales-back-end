package br.com.ifba.prg04ultragas.usuario.controller;

import br.com.ifba.prg04ultragas.usuario.model.Usuario;
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
    public List<Usuario> listarUsuarios() {

        return service.listarUsuarios();
    }

    // Salva um novo usuário no banco
    @PostMapping
    public Usuario salvarUsuario(@RequestBody Usuario usuario) {

        return service.salvarUsuario(usuario);
    }

    // Busca usuário por Id
    @GetMapping("/{id}")
    public Usuario buscarUsuarioPorId(@PathVariable Long id) {
        return service.buscarUsuarioPorId(id);
    }

    // Atualiza os dados de um usuário existente
    @PutMapping("/{id}")
    public Usuario atualizarUsuario(
            @PathVariable Long id,
            @RequestBody Usuario usuario
    ) {

        return service.atualizarUsuario(id, usuario);
    }

    // Remove um usuário pelo ID
    @DeleteMapping("/{id}")
    public void deletarUsuario(@PathVariable Long id) {

        service.deletarUsuario(id);
    }
}