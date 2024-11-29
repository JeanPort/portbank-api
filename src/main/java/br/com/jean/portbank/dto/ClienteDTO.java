package br.com.jean.portbank.dto;

import br.com.jean.portbank.model.Cliente;



public record ClienteDTO(String nome, String email, String cpf, String telefone, String senha) {

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
