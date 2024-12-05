package br.com.jean.portbank.controller;

import br.com.jean.portbank.dto.ContaDTO;
import br.com.jean.portbank.dto.ResponseDTO;
import br.com.jean.portbank.service.conta.IContaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class ContaController {

    @Autowired
    private IContaService service;

    @PostMapping("/contas")
    public ResponseEntity<ResponseDTO> create(@RequestBody ContaDTO conta){

            Integer res = service.cadastrarNovaConta(conta);
            if (res == null){
                return ResponseEntity.badRequest().body(new ResponseDTO("Conta com cliente invalido"));
            }
            return ResponseEntity.status(201).body(new ResponseDTO("Conta criada com sucesso numero " + res));

    }
}
