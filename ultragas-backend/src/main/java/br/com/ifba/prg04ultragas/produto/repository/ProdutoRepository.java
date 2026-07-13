package br.com.ifba.prg04ultragas.produto.repository;

import br.com.ifba.prg04ultragas.produto.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

// Repositório responsável pelas operações no banco
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}