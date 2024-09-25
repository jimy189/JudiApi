package com.br.judiapi.repository;

import com.br.judiapi.entity.Processo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessoRepository extends JpaRepository<Processo, Long> {
    boolean existsByNumero(String numero);

    Processo findByNumero(String numero);
}
