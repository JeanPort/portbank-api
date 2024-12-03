package br.com.jean.portbank.service.conta;

import br.com.jean.portbank.dto.ContaDTO;
import br.com.jean.portbank.model.Cliente;
import br.com.jean.portbank.model.Conta;

import java.util.List;

public interface IContaService {

    Integer cadastrarNovaConta(ContaDTO dto);
    List<Conta> recuperarPorCliente(Cliente cliente);
    Conta recuperarPorNumeroConta(Integer numeroConta);
}
