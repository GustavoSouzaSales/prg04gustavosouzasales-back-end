package br.com.ifba.prg04ultragas.pagamento.repository;

import br.com.ifba.prg04ultragas.pagamento.model.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
}