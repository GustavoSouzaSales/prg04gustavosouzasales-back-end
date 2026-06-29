package br.com.ifba.prg04ultragas.entrega.repository;

import br.com.ifba.prg04ultragas.entrega.model.Entrega;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntregaRepository extends JpaRepository<Entrega, Long> {
}