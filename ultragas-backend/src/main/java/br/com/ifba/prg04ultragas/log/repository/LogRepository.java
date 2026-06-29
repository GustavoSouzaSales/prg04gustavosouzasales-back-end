package br.com.ifba.prg04ultragas.log.repository;

import br.com.ifba.prg04ultragas.log.model.Log;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<Log, Long> {
}