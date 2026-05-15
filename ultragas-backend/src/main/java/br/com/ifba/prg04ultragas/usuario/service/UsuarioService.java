package br.com.ifba.prg04ultragas.usuario.service;

import br.com.ifba.prg04ultragas.usuario.model.Usuario;
import br.com.ifba.prg04ultragas.usuario.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // Define essa classe como camada de serviço
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    // Busca todos os usuários no banco
    public List<Usuario> listarUsuarios() {

        return repository.findAll();
    }

    // Salva um novo usuário
    public Usuario salvarUsuario(Usuario usuario) {

        return repository.save(usuario);
    }

    // Atualiza os dados de um usuário existente
    public Usuario atualizarUsuario(Long id, Usuario usuarioAtualizado) {

        // Procura o usuário pelo ID
        Usuario usuario = repository.findById(id).orElseThrow();

        // Atualiza os dados
        usuario.setNome(usuarioAtualizado.getNome());
        usuario.setEmail(usuarioAtualizado.getEmail());

        // Salva novamente no banco
        return repository.save(usuario);
    }

    // Remove um usuário pelo ID
    public void deletarUsuario(Long id) {

        repository.deleteById(id);
    }
}