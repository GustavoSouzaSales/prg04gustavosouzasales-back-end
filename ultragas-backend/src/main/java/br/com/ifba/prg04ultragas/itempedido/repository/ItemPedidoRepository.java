package br.com.ifba.prg04ultragas.itempedido.repository;

import br.com.ifba.prg04ultragas.itempedido.model.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


// Repositório responsável pelas operações no banco
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
    Page<ItemPedido> findByPedidoId(Long pedidoId, Pageable pageable);
}