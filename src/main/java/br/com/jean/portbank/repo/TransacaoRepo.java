package br.com.jean.portbank.repo;

import br.com.jean.portbank.model.Conta;
import br.com.jean.portbank.model.Transacao;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TransacaoRepo extends CrudRepository<Transacao, Long> {

    List<Transacao> findAllByContaAndDataHoraBetween(Conta conta, LocalDateTime inicio, LocalDateTime fim);
}
