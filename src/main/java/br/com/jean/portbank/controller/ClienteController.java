package br.com.jean.portbank.controller;

import br.com.jean.portbank.dto.ClienteDTO;
import br.com.jean.portbank.dto.ResponseDTO;
import br.com.jean.portbank.service.cliente.IClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class ClienteController {

    @Autowired
    private IClienteService service;

    @PostMapping("/clientes")
    public ResponseEntity<ResponseDTO> cadastrar(@Valid @RequestBody ClienteDTO cliente) {

        try {
            Integer res = service.cadastrarCliente(cliente);
            if (res == null) {
                return ResponseEntity.badRequest().body(new ResponseDTO("Dados ja existentes"));
            }
            return ResponseEntity.status(201).body(new ResponseDTO("Cliente cadastrado " + res));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDTO("Dados incompletos"));
        }
    }
}
