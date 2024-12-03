package br.com.jean.portbank.dto;

import br.com.jean.portbank.model.Cliente;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;


public record ClienteDTO(
        @NotNull String nome,
        @NotNull @Email String email,
        @NotNull @CPF String cpf,
        @NotNull @Min(11) String telefone,
        @NotNull @Min(8) String senha
) {

    public Cliente toCliente(){
        Cliente cliente = new Cliente();
        cliente.setNome(this.nome);
        cliente.setCpf(this.cpf);
        cliente.setTelefone(this.telefone);
        cliente.setEmail(this.email);
        cliente.setSenha(this.senha);
        return cliente;
    }
}
