package br.com.ifba.prg04ultragas.entrega.repository;

import br.com.ifba.prg04ultragas.entrega.model.Entrega;
import org.springframework.data.jpa.repository.JpaRepository;

// Repositório responsável pelas operações no banco
public interface EntregaRepository extends JpaRepository<Entrega, Long> {
}