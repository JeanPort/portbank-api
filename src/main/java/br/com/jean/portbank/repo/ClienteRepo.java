package br.com.jean.portbank.repo;

import br.com.jean.portbank.model.Cliente;
import org.springframework.data.repository.CrudRepository;

public interface ClienteRepo extends CrudRepository<Cliente,Integer> {

    Cliente findByEmailOrCpfOrTelefone(String email, String cpf, String telefone);
}
