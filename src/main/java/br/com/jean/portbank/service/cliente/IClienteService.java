package br.com.jean.portbank.service.cliente;

import br.com.jean.portbank.dto.ClienteDTO;

public interface IClienteService {

    Integer cadastrarCliente(ClienteDTO dto);
    Integer alterarDados(ClienteDTO dto);
}
