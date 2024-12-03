package br.com.jean.portbank.repo;

import br.com.jean.portbank.model.Cliente;
import br.com.jean.portbank.model.Conta;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ContaRepo extends CrudRepository<Conta, Integer> {

    List<Conta> findAllByCliente(Cliente c);
}
