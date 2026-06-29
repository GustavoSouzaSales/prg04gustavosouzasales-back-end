package br.com.ifba.prg04ultragas.endereco.repository;

import br.com.ifba.prg04ultragas.endereco.model.Endereco;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

    Page<Endereco> findByUsuarioId(Long usuarioId, Pageable pageable);

}