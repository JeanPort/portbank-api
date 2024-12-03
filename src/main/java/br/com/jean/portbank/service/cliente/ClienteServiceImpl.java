package br.com.jean.portbank.service.cliente;

import br.com.jean.portbank.dto.ClienteDTO;
import br.com.jean.portbank.model.Cliente;
import br.com.jean.portbank.repo.ClienteRepo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClienteServiceImpl implements IClienteService{

    @Autowired
    private ClienteRepo repo;

    @Override
    public Integer cadastrarCliente(@Valid ClienteDTO dto) {
        Cliente res = repo.findByEmailOrCpfOrTelefone(dto.email(), dto.cpf(), dto.telefone());
        if (res != null) {
            return null;
        }
        return repo.save(dto.toCliente()).getIdCliente();
    }

    @Override
    public Integer alterarDados(ClienteDTO dto) {
        return repo.save(dto.toCliente()).getIdCliente();
    }
}
