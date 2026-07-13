package br.com.ifba.prg04ultragas.notificacao.repository;

import br.com.ifba.prg04ultragas.notificacao.model.Notificacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Repositório responsável pelas operações no banco
public interface NotificacaoRepository extends JpaRepository<Notificacao, Long> {

    List<Notificacao> findByUsuarioIdOrderByDataCriacaoDesc(Long usuarioId);
}