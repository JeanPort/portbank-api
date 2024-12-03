package br.com.jean.portbank.dto;


import br.com.jean.portbank.model.Cliente;
import br.com.jean.portbank.model.Conta;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record ContaDTO(
                       @NotNull Integer numeroBanco,
                       @NotNull Integer numeroAgenci,
                       @NotNull @PositiveOrZero Double saldo,
                       @NotNull @PositiveOrZero Double limite,
                       @NotNull Integer idCliente
) {

    public Conta toConta() {
        Conta conta = new Conta();
        conta.setNumeroBanco(numeroBanco);
        conta.setNumeroAgenci(numeroAgenci);
        conta.setSaldo(saldo);
        conta.setLimite(limite);
        conta.setAtiva(1);
        Cliente cliente = new Cliente();
        cliente.setIdCliente(idCliente);
        conta.setCliente(cliente);
        return conta;
    }

}
