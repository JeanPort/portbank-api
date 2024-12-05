package br.com.jean.portbank.controller;

import br.com.jean.portbank.dto.PagamentoDTO;
import br.com.jean.portbank.dto.ResponseDTO;
import br.com.jean.portbank.exception.InvalidAccountException;
import br.com.jean.portbank.exception.NotEnoughBalanceException;
import br.com.jean.portbank.service.transacao.ITransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class TransacaoController {

    @Autowired
    private ITransacaoService service;

    @PostMapping("/pagamentos")
    public ResponseEntity<ResponseDTO> pagamento(@RequestBody PagamentoDTO pagamentoDTO) {
        try {
            if (service.efetuarPagamento(pagamentoDTO)) {
                return ResponseEntity.status(201).body(new ResponseDTO("Pagamento realizado com sucesso"));
            }

            return ResponseEntity.badRequest().body(new ResponseDTO("Não foi possivel realzar o pagameto"));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDTO(e.getMessage()));
        }
    }
}
